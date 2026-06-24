package pl.atipera.recruitment;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import java.util.List;

@HttpExchange
public interface GitHubClient {

    @GetExchange("/users/{username}/repos")
    List<Dto.GitHubRepo> fetchRepositories(@PathVariable("username") String username);

    @GetExchange("/repos/{owner}/{repo}/branches")
    List<Dto.GitHubBranch> fetchBranches(@PathVariable("owner") String owner, @PathVariable("repo") String repo);
}