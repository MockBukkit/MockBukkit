# Changelog
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]
### Added
 - Add `MockBukkit.loadJar(string)` which allows loading other plugins from jar files.
 - Add `MockBukkit.mock(ServerMock)` which allows custom server implementations.
 - Add Maven instructions to `README.md`
 - Implement many player related methods
 - Add `PersistentData` containers
 - Add `EnderChestInventoryMock`
 - Add `EnchantedBookMeta` mocks

### Fixed
 - Fix several constructors for `ItemMeta`

### Changed
 - Update to Minecraft 1.15
 - Rename `MockBukkit#unload()` to `MockBukkit#unmock()` for symmetry.

### Removed

## [0.2.2] - 2020-04-17
### Added
- Add CHANGELOG.md
- Implement `Player#getEyeLocation`, `Player#getEyeHeight()`, and `Player#getEyeHeight(boolean)`

### Fixed
- Out-of-date maven dependency information in README

[Unreleased]: https://github.com/seeseemelk/MockBukkit
[0.2.2]: https://search.maven.org/artifact/com.github.seeseemelk/MockBukkit-v1.14/0.2.2/jar
[0.2.1]: https://search.maven.org/artifact/com.github.seeseemelk/MockBukkit-v1.14/0.2.1/jar
