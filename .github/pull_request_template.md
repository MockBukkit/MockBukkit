# Checklist
Before the pull request can be accepted, the version number stored in settings.gradle should be updated.
MockBukkit uses semantic-versioning (https://semver.org/)
 - If the PR fixes a bug in MockBukkit, update the patch version. (if the current version is `0.4.1`, the new version should be `0.4.2`)
 - If the PR adds a new feature to MockBukkit, update minor version. (if the current version is `0.4.1`, the new version should be `0.5.0`)

Note that a PR that fixes an `UnimplementedOperationException`, it should be considered a new version and not a bugfix.

The version number can be found in `settings.gradle`.
