package pl.atipera.recruitment;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import java.util.List;

@Component
class GitHubClient {

    private final RestClient restClient;
    private final String baseUrl;

    GitHubClient(RestClient restClient, @Value("${github.api.url:https://api.github.com}") String baseUrl) {
        this.restClient = restClient;
        this.baseUrl = baseUrl;
    }

    List<GitHubRepo> getUserRepositories(String username) {
        return restClient.get()
                .uri(baseUrl + "/users/" + username + "/repos")
                .retrieve()
                .onStatus(status -> status.value() == 404, (request, response) -> {
                    throw new UserNotFoundException("User with username '" + username + "' not found on GitHub");
                })
                .body(new ParameterizedTypeReference<List<GitHubRepo>>() {});
    }

    List<GitHubBranch> getRepositoryBranches(String username, String repoName) {
        return restClient.get()
                .uri(baseUrl + "/repos/" + username + "/" + repoName + "/branches")
                .retrieve()
                .body(new ParameterizedTypeReference<List<GitHubBranch>>() {});
    }
}