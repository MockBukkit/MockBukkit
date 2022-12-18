# Description
State the changes of the pull request here.

# Checklist
The following items should be checked before the pull request can be merged.

- [ ] Unit tests added.
- [ ] Code follows existing code format.

# Info on creating a pull request
- Make sure that unit tests are added which test the relevant changes.
- Make sure that the changes follow the existing code format.

# For maintainer
When a PR is approved, the maintainer should label this PR with `release/*`.

- If the PR fixes a bug in MockBukkit, add the `release/patch` Tag.
- If the PR adds a new feature to MockBukkit, add  the `release/minor` Tag.

Note that a PR that fixes an `UnimplementedOperationException` should be considered a new version and not a bugfix.
