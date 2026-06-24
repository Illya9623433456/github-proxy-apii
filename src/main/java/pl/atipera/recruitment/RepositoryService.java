package pl.atipera.recruitment;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Service
public class RepositoryService {

    private final GitHubClient gitHubClient;
    private final Executor virtualThreadExecutor = Executors.newVirtualThreadPerTaskExecutor();

    public RepositoryService(GitHubClient gitHubClient) {
        this.gitHubClient = gitHubClient;
    }

    public List<Dto.RepositoryResponse> getUserRepositories(String username) {
        List<Dto.GitHubRepo> repos = gitHubClient.fetchRepositories(username);

        List<CompletableFuture<Dto.RepositoryResponse>> futures = repos.stream()
                .filter(repo -> !repo.fork())
                .map(repo -> CompletableFuture.supplyAsync(() -> {
                    List<Dto.GitHubBranch> branches = gitHubClient.fetchBranches(repo.owner().login(), repo.name());

                    List<Dto.BranchInfo> branchInfos = branches.stream()
                            .map(b -> new Dto.BranchInfo(b.name(), b.commit().sha()))
                            .toList();

                    return new Dto.RepositoryResponse(repo.name(), repo.owner().login(), branchInfos);
                }, virtualThreadExecutor))
                .toList();

        return futures.stream()
                .map(CompletableFuture::join)
                .toList();
    }
}