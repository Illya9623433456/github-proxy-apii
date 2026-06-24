package pl.atipera.recruitment;

import java.util.List;

public class Dto {
    public record GitHubRepo(String name, Owner owner, boolean fork) {}
    public record Owner(String login) {}
    public record GitHubBranch(String name, Commit commit) {}
    public record Commit(String sha) {}

    public record RepositoryResponse(String repositoryName, String ownerLogin, List<BranchInfo> branches) {}
    public record BranchInfo(String name, String lastCommitSha) {}
    public record ErrorResponse(int status, String message) {}
}