For Maintainers
===============

Pull Requests
~~~~~~~~~~~~~
When a pull request is created, the maintainer should label it with ``release/*``.

| If the PR fixes a bug, update the patch version (``release/patch``).
| If the PR adds a feature (fixing an ``UnimplementedOperationException`` is a feature), update the minor version (``release/minor``).

Major versions should only be incremented after discussion with the entire team.
