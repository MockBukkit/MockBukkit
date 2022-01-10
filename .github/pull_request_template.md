# Description
State the changes of the pull request here.

# Checklist
The following items should be checked before the pull request can be merged.
- [ ] Version number updated in `settings.gradle` and `Readme.md`.
- [ ] Unit tests added.
- [ ] Code follows existing code format.

# Info on creating a pull request
Before the pull request can be accepted, the version number stored in settings.gradle should be updated.
MockBukkit uses semantic-versioning (https://semver.org/)

 - If the PR fixes a bug in MockBukkit, update the patch version. (if the current version is `0.4.1`, the new version should be `0.4.2`)
 - If the PR adds a new feature to MockBukkit, update minor version. (if the current version is `0.4.1`, the new version should be `0.5.0`)
 - Make sure that unit tests are added which test the relevant changes.
 - Make sure that the changes follow the existing code format.

Note that a PR that fixes an `UnimplementedOperationException` should be considered a new version and not a bugfix.

The version number can be found in `settings.gradle`.
