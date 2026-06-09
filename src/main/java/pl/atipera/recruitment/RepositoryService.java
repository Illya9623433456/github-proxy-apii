package pl.atipera.recruitment;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
class RepositoryService {

    private final GitHubClient gitHubClient;

    RepositoryService(GitHubClient gitHubClient) {
        this.gitHubClient = gitHubClient;
    }

    List<RepositoryResponse> getUserRepositories(String username) {
        List<GitHubRepo> repos = gitHubClient.getUserRepositories(username);
        return repos.stream()
                .filter(repo -> !repo.fork())
                .map(repo -> {
                    List<GitHubBranch> gitHubBranches = gitHubClient.getRepositoryBranches(username, repo.name());

                    List<BranchResponse> branches = gitHubBranches.stream()
                            .map(branch -> new BranchResponse(branch.name(), branch.commit().sha()))
                            .collect(Collectors.toList());

                    return new RepositoryResponse(repo.name(), repo.owner().login(), branches);
                })
                .collect(Collectors.toList());
    }
}