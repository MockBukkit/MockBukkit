Contributing Code to MockBukkit
===============================

## :toolbox: Software requirements

To get started contributing to MockBukkit, you will need a few things.

- [Git](https://git-scm.com/downloads)
- [Java 17 or newer](https://adoptium.net/).

## :wrench: Setting up the project

The first step in contributing to MockBukkit, is to create a fork of it. You can do this by clicking the `Fork` button
in the top-right of the GitHub repository.
Once your fork is created, you will need to clone it to your local system.

To do this, on GitHub, click on the green `Code` button on the homepage of your fork, then copy the provided URL.
Next, if on Windows open up `Git Bash` and navigate to where you want to clone the repo.
If on Linux, simply navigate to where you want to clone the repo.

Once you're where you want to clone the repo, run `git clone <url>` with `<url>` being the URL you just copied, omitting
the `<>`.

When the previous command is finished executing, you will need to create your own branch.
To do this, run `git checkout -b <branch name>` with `<branch name>` being the name of your new branch.
There cannot be two branches with the same name. The recommended naming scheme for branch is `feature/my-feature` for
adding something new, and `fix/my-fix` for fixing a bug.
`my-feature`/ `my-fix` should obviously be a condensed name of what you're adding/ fixing.

Now you can get to coding!

## :black_nib: Code formatting

Every larger project has a certain formatting style which dictates how code is formatted.
If you're using a modern IDE like Intellij, we have a `.editorconfig` file within the project which will automatically
tell your IDE how our code should be formatted.
This allows you to use auto code formatting without disrupting the project's code style.

We would appreciate it if all pull requests adhere to this project's code style.
If for whatever reason your IDE does not support this or enforces a different code style, try to manually keep the style
as consistent as possible in regards to the surrounding code.
But don't worry, we won't reject your hard work just because of a misaligned bracket.
We will help you out with formatting mistakes by suggesting changes to your PR!

## :books: Writing documentation

Code documentation is a great way to improve the maintainability of this project.
Of course we do not expect you to add JavaDocs to methods that are just simple getters, setters or simply override
methods from the Bukkit API.

But documentation does help us review your changes more efficiently and also enables us to maintain these changes in the
future much more effectively.
So, when you add new methods or classes - that are specific to MockBukkit - JavaDocs will be mandatory.
But adding a short JavaDocs section to any class - including mocks but excluding tests - is also very much appreciated
❤️.
When inside a method, using single-line comments before some statements can also help to improve the readibility of the
code. Just a few short explanatory comments in cases where you expect some confusion can go a long way!

## :pencil2: Writing unit tests

All code you write should have unit tests to accompany it.
The unit test class is always the same fully qualified name, but with `Test` appended to the end.
Please create tests that cover all possible execution paths and not just one, assuming there is more than one.

## :test_tube: Testing your changes

Once you've completed your changes and wrote tests for them, you can test to see if they're working by running the
Gradle `test` task.
If on Linux, you can run `./gradlew test` from the terminal.
If on Windows, you can run `gradlew.bat test` from the command prompt.
If using an IDE like IntelliJ, you can open the Gradle panel, navigate to `MockBukkit-1.xx` -> `Tasks` -> `verification`
, then double click `test`.

## :hammer_and_wrench: Committing and pushing your changes

Once you've completed your changes and confirmed that they work, you'll need to commit, then push your changes.
To commit your changes, run `git add .` to add all your changes, then `git commit -m "<commit message>"`
with `<commit message>` stating what you've done in 70 characters or less.
Now you're ready to push your changes to GitHub. To do this, run `git push -u origin <branch name>` with `<branch name>`
being the name of your branch you created earlier.

## :star: Creating your pull request

To create the pull request, head back to your fork on GitHub, and go to the `Pull Requests` tab, and click
the `New Pull Request` button.
On the left, make sure `Base repository` is set to `MockBukkit/MockBukkit` and the branch next to it is set to the
latest version (e.g. `v1.20`).
On the right, make sure `Head repoitory` is set to `Usename/MockBukkit` with `Username` being your GitHub username and
the branch being the new branch you created.
Now you can click the `Create pull request` button to start creating the pull request.

The title of your pull request should be a should description of what you changed, in 70 characters or less.
In the description box, *please* follow the template provided and fill out everything that's asked for.

Make sure `Allow edits and access to secrets by maintainers` is checked, so we can update and make small tweaks to your
pull request, so you don't have to.

If you scroll down, you will see the diff between MockBukkit's source code, and the code you modified.
If everything in their looks alright, you can click the `Create pull request` button to create the pull request!

Initially GitHub will show that the pull request did not pass checks. If you scroll down to the bottom, you will see a
list of all checks and their status.
If all checks have a green checkmark except for `Check PR has release/* label`, you're good to go, and it will succeed
as soon as a maintainer assigns a label to your pull request.

If the `Build` check is failing, this means that there was an error when compiling your code or a test failed.
To see why this happened, you can click `Details` then `build` to see the Gradle log. If you're unsure of how to fix it,
you can wait for a maintainer to help.

A few minutes after you've created your pull request, `SonarCloud` will leave a comment on the quality of your code.
If everything is `0`, you're good to go! If SonarCloud has issues, you can click on them, and it will show you what's
wrong and how you should fix it.
Usually these are reasonable, and we would appreciate if you resolved them.
