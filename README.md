![](https://runelite.net/img/logo.png)
# runelite [![Travis](https://img.shields.io/travis/runelite/runelite.svg)](https://travis-ci.org/runelite/runelite) [![Discord](https://img.shields.io/discord/301497432909414422.svg)](https://discord.gg/mePCs8U)

RuneLite is a free, open source OldSchool RuneScape client.

If you have any questions, please join our IRC channel on [irc.rizon.net #runelite](http://qchat.rizon.net/?channels=runelite&uio=d4) or alternatively our [Discord](https://discord.gg/mePCs8U) server.

## Project Layout

- [cache](cache/src/main/java/net/runelite/cache) - Libraries used for reading/writing cache files, as well as the data in it
- [http-api](http-api/src/main/java/net/runelite/http/api) - API for api.runelite.net
- [http-service](http-service/src/main/java/net/runelite/http/service) - Service for api.runelite.net
- [runelite-api](runelite-api/src/main/java/net/runelite/api) - RuneLite API, interfaces for accessing the client
- [runelite-mixins](runelite-mixins/src/main/java/net/runelite) - Mixins which are injected into the injected client's classes
- [runescape-api](runescape-api/src/main/java/net/runelite) - Mappings correspond to these interfaces, runelite-api is a subset of this
- [runelite-client](runelite-client/src/main/java/net/runelite/client) - Game client with plugins

## Usage

Open the project in your IDE as a Maven project, build the root module and then run the RuneLite class in runelite-client.  
For more information visit the [RuneLite Wiki](https://github.com/runelite/runelite/wiki).

### License

RuneLite is licensed under the BSD 2-clause license. See the license header in the respective file to be sure.

## Contribute and Develop

We've set up a separate document for our [contribution guidelines](https://github.com/runelite/runelite/blob/master/.github/CONTRIBUTING.md).

Post---------------

Navigating the UI while fighting in runescape can be hard, especially for those who don't have easily accessible
function keys. Here is my proposed solution to this problem, heavily inspired by modal and prefix hotkey software (vi/vim, tmux, etc) and chat systems in basically any modern game.

An attempt at a brief explanation of this: When playing RuneScape, WASD will control the camera, and nearby keys can be remapped to support interface tab navigation.
When the player wants to chat, they can do a few things: tab will reply to a private message and put the player into typing mode, enter or t will simply put the player into typing mode intended for public chat,
and c will passthrough the character '/' and put the player into typing mode intended for clan chat. As soon as the player hits enter, they will return back to playing/combat mode. If the player wants to use the mouse
for camera movement, they can also remap WASD (or any key really) to interface tabs as well.

Here is the alternative explanation using a very simple syntax explaining some example binds:

```
modes: [playing, typing]

# Clan chat type
playing - c : (Passthrough /) mode->typing

# Public chat
playing - enter : mode->typing
playing - t : mode->typing

playing - tab : (Passthrough tab) mode->typing

# Camera controls
playing - w : UP
playing - a : LEFT
playing - s : DOWN
playing - d : RIGHT

# Interface controls (Example)
playing - q : INVENTORY_TAB
playing - e : PRAYER_TAB
playing - r : EQUIP_TAB
playing - f : ATTACK_TAB
playing - x : MAGIC_TAB

# Typing mode binds
typing - enter : (Passthrough enter) mode->playing
typing + shift - enter : (Passthrough enter) # stay in typing mode
```