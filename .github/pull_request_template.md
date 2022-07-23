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

- If the PR fixes a bug in MockBukkit, update the patch version. (if the current version is `0.4.1`, the new version
  should be `0.4.2`)
- If the PR adds a new feature to MockBukkit, update minor version. (if the current version is `0.4.1`, the new version
  should be `0.5.0`)

Note that a PR that fixes an `UnimplementedOperationException` should be considered a new version and not a bugfix.
