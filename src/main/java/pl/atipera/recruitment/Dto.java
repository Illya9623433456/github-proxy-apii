package pl.atipera.recruitment;

import java.util.List;

record RepositoryResponse(String repositoryName, String ownerLogin, List<BranchResponse> branches) {}
record BranchResponse(String name, String lastCommitSha) {}
record ErrorResponse(int status, String message) {}

record GitHubRepo(String name, GitHubOwner owner, boolean fork) {}
record GitHubOwner(String login) {}
record GitHubBranch(String name, GitHubCommit commit) {}
record GitHubCommit(String sha) {}