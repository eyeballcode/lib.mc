/*
 * 	Copyright (C) 2016 Eyeballcode
 *
 * 	This program is free software: you can redistribute it and/or modify
 * 	it under the terms of the GNU General Public License as published by
 * 	the Free Software Foundation, either version 3 of the License, or
 * 	(at your option) any later version.
 *
 * 	This program is distributed in the hope that it will be useful,
 * 	but WITHOUT ANY WARRANTY; without even the implied warranty of
 * 	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * 	GNU General Public License for more details.
 *
 * 	You should have received a copy of the GNU General Public License
 * 	along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * 	See LICENSE.MD for more details.
 */

package lib.mc.player;

import lib.mc.util.Utils;

import java.util.UUID;

public class Player {

    private String uuid, name, username;
    private boolean legacy, demo;
    private UserData userData;

    public Player(String id, String username, String name, boolean legacy, boolean demo, UserData userData) {
        uuid = Utils.parseUUID(id.replaceAll("-", ""));
        this.name = name;
        this.legacy = legacy;
        this.demo = demo;
        this.userData = userData;
        this.username = username;
    }

    /**
     * Gets the player's username (Usually email)
     * @return
     */
    public String getUsername() {
        return username;
    }

    /**
     *
     * Gets the user properties:
     *
     * {
     *     twitch_access_token: "Blah"
     * }
     *
     * @return The user data
     */
    public UserData getUserData() {
        return userData;
    }

    /**
     * Checks if the account is demo
     *
     * @return If the account is demo.
     */
    public boolean isDemo() {
        return demo;
    }

    /**
     * Checks if the account is legacy
     *
     * @return If the account is legacy
     */
    public boolean isLegacy() {
        return legacy;
    }

    /**
     * Get the player's display name (Usually something like Eyeballcode)
     *
     * @return The player name
     */
    public String getName() {
        return name;
    }

    /**
     * Get the player UUID
     *
     * @return The player UUID
     */
    public UUID getUUID() {
        return UUID.fromString(uuid);
    }

    public String getUUIDMCFormat() {
        return getUUID().toString().replaceAll("-", "");
    }


    @Override
    public String toString() {
        return name + "@" + getUUID();
    }
}
