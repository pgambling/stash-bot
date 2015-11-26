# stash-bot

Raspberry PI based robot that responds to actions on Stash repositories.

## Overview

This started from a discussion about how it is said in the movie "It's a Wonderful Life" that an angel gets it's wings everytime a bell rings. We then thought about adapting that to stash pull requests (yes, it is ironic that I am hosting an Atlassian Stash centric repo on github). If someone would ring a bell everytime they merged code to the master branch, an angel could get its wings. Finally, as any good developer does, this task should be automated rather than expecting a human being to remember to pick up and ring the bell manually each time. Thus the idea to build a small machine to ring a real live bell whenever a pull request is merged was born.

Enter Stash Bot...

## Setup

Build your project once with the following script and then run 'lein run' to execute the program.

    ./scripts/build

To auto build your project in dev mode:

    ./scripts/watch

To start an auto-building Node REPL (requires
[rlwrap](http://utopia.knoware.nl/~hlub/uck/rlwrap/), on OS X
installable via brew):

    ./scripts/repl

To get source map support in the Node REPL:

    lein npm install

Clean project specific out:

    lein clean

## License

ISC License

Copyright (c) 2015, Philippe Gambling

Permission to use, copy, modify, and/or distribute this software for any
purpose with or without fee is hereby granted, provided that the above
copyright notice and this permission notice appear in all copies.

THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES
WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF
MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR
ANY SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES
WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN
ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF
OR IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
