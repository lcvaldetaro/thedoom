/* Emacs style mode select   -*- C++ -*-
 *-----------------------------------------------------------------------------
 *
 *
 *  PrBoom: a Doom port merged with LxDoom and LSDLDoom
 *  based on BOOM, a modified and improved DOOM engine
 *  Copyright (C) 1999 by
 *  id Software, Chi Hoang, Lee Killough, Jim Flynn, Rand Phares, Ty Halderman
 *  Copyright (C) 1999-2000 by
 *  Jess Haas, Nicolas Kalkhof, Colin Phipps, Florian Schulze
 *  Copyright 2005, 2006 by
 *  Florian Schulze, Colin Phipps, Neil Stevens, Andrey Budko
 *
 *  This program is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU General Public License
 *  as published by the Free Software Foundation; either version 2
 *  of the License, or (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA
 *  02111-1307, USA.
 *
 * DESCRIPTION:
 *      Created by a sound utility.
 *      Kept as a sample, DOOM2 sounds.
 *
 *-----------------------------------------------------------------------------*/

// killough 5/3/98: reformatted

#ifdef HAVE_CONFIG_H

#include "config.h"

#endif

#include "doomtype.h"
#include "sounds.h"

//
// Information about all the music
//

musicinfo_t S_music[] = {
        {0},
        {"e1m1",   0},
        {"e1m2",   0},
        {"e1m3",   0},
        {"e1m4",   0},
        {"e1m5",   0},
        {"e1m6",   0},
        {"e1m7",   0},
        {"e1m8",   0},
        {"e1m9",   0},
        {"e2m1",   0},
        {"e2m2",   0},
        {"e2m3",   0},
        {"e2m4",   0},
        {"e2m5",   0},
        {"e2m6",   0},
        {"e2m7",   0},
        {"e2m8",   0},
        {"e2m9",   0},
        {"e3m1",   0},
        {"e3m2",   0},
        {"e3m3",   0},
        {"e3m4",   0},
        {"e3m5",   0},
        {"e3m6",   0},
        {"e3m7",   0},
        {"e3m8",   0},
        {"e3m9",   0},
        {"inter",  0},
        {"intro",  0},
        {"bunny",  0},
        {"victor", 0},
        {"introa", 0},
        {"runnin", 0},
        {"stalks", 0},
        {"countd", 0},
        {"betwee", 0},
        {"doom",   0},
        {"the_da", 0},
        {"shawn",  0},
        {"ddtblu", 0},
        {"in_cit", 0},
        {"dead",   0},
        {"stlks2", 0},
        {"theda2", 0},
        {"doom2",  0},
        {"ddtbl2", 0},
        {"runni2", 0},
        {"dead2",  0},
        {"stlks3", 0},
        {"romero", 0},
        {"shawn2", 0},
        {"messag", 0},
        {"count2", 0},
        {"ddtbl3", 0},
        {"ampie",  0},
        {"theda3", 0},
        {"adrian", 0},
        {"messg2", 0},
        {"romer2", 0},
        {"tense",  0},
        {"shawn3", 0},
        {"openin", 0},
        {"evil",   0},
        {"ultima", 0},
        {"read_m", 0},
        {"dm2ttl", 0},
        {"dm2int", 0},
};


//
// Information about all the sfx
//

sfxinfo_t S_sfx[] = {
        // S_sfx[0] needs to be a dummy for odd reasons.
        {"none", xfalse, 0, 0, -1, -1, 0},

        {"pistol", xfalse, 64, 0, -1, -1, 0},
        {"shotgn", xfalse, 64, 0, -1, -1, 0},
        {"sgcock", xfalse, 64, 0, -1, -1, 0},
        {"dshtgn", xfalse, 64, 0, -1, -1, 0},
        {"dbopn", xfalse, 64, 0, -1, -1, 0},
        {"dbcls", xfalse, 64, 0, -1, -1, 0},
        {"dbload", xfalse, 64, 0, -1, -1, 0},
        {"plasma", xfalse, 64, 0, -1, -1, 0},
        {"bfg", xfalse, 64, 0, -1, -1, 0},
        {"sawup", xfalse, 64, 0, -1, -1, 0},
        {"sawidl", xfalse, 118, 0, -1, -1, 0},
        {"sawful", xfalse, 64, 0, -1, -1, 0},
        {"sawhit", xfalse, 64, 0, -1, -1, 0},
        {"rlaunc", xfalse, 64, 0, -1, -1, 0},
        {"rxplod", xfalse, 70, 0, -1, -1, 0},
        {"firsht", xfalse, 70, 0, -1, -1, 0},
        {"firxpl", xfalse, 70, 0, -1, -1, 0},
        {"pstart", xfalse, 100, 0, -1, -1, 0},
        {"pstop", xfalse, 100, 0, -1, -1, 0},
        {"doropn", xfalse, 100, 0, -1, -1, 0},
        {"dorcls", xfalse, 100, 0, -1, -1, 0},
        {"stnmov", xfalse, 119, 0, -1, -1, 0},
        {"swtchn", xfalse, 78, 0, -1, -1, 0},
        {"swtchx", xfalse, 78, 0, -1, -1, 0},
        {"plpain", xfalse, 96, 0, -1, -1, 0},
        {"dmpain", xfalse, 96, 0, -1, -1, 0},
        {"popain", xfalse, 96, 0, -1, -1, 0},
        {"vipain", xfalse, 96, 0, -1, -1, 0},
        {"mnpain", xfalse, 96, 0, -1, -1, 0},
        {"pepain", xfalse, 96, 0, -1, -1, 0},
        {"slop", xfalse, 78, 0, -1, -1, 0},
        {"itemup", xtrue, 78, 0, -1, -1, 0},
        {"wpnup", xtrue, 78, 0, -1, -1, 0},
        {"oof", xfalse, 96, 0, -1, -1, 0},
        {"telept", xfalse, 32, 0, -1, -1, 0},
        {"posit1", xtrue, 98, 0, -1, -1, 0},
        {"posit2", xtrue, 98, 0, -1, -1, 0},
        {"posit3", xtrue, 98, 0, -1, -1, 0},
        {"bgsit1", xtrue, 98, 0, -1, -1, 0},
        {"bgsit2", xtrue, 98, 0, -1, -1, 0},
        {"sgtsit", xtrue, 98, 0, -1, -1, 0},
        {"cacsit", xtrue, 98, 0, -1, -1, 0},
        {"brssit", xtrue, 94, 0, -1, -1, 0},
        {"cybsit", xtrue, 92, 0, -1, -1, 0},
        {"spisit", xtrue, 90, 0, -1, -1, 0},
        {"bspsit", xtrue, 90, 0, -1, -1, 0},
        {"kntsit", xtrue, 90, 0, -1, -1, 0},
        {"vilsit", xtrue, 90, 0, -1, -1, 0},
        {"mansit", xtrue, 90, 0, -1, -1, 0},
        {"pesit", xtrue, 90, 0, -1, -1, 0},
        {"sklatk", xfalse, 70, 0, -1, -1, 0},
        {"sgtatk", xfalse, 70, 0, -1, -1, 0},
        {"skepch", xfalse, 70, 0, -1, -1, 0},
        {"vilatk", xfalse, 70, 0, -1, -1, 0},
        {"claw", xfalse, 70, 0, -1, -1, 0},
        {"skeswg", xfalse, 70, 0, -1, -1, 0},
        {"pldeth", xfalse, 32, 0, -1, -1, 0},
        {"pdiehi", xfalse, 32, 0, -1, -1, 0},
        {"podth1", xfalse, 70, 0, -1, -1, 0},
        {"podth2", xfalse, 70, 0, -1, -1, 0},
        {"podth3", xfalse, 70, 0, -1, -1, 0},
        {"bgdth1", xfalse, 70, 0, -1, -1, 0},
        {"bgdth2", xfalse, 70, 0, -1, -1, 0},
        {"sgtdth", xfalse, 70, 0, -1, -1, 0},
        {"cacdth", xfalse, 70, 0, -1, -1, 0},
        {"skldth", xfalse, 70, 0, -1, -1, 0},
        {"brsdth", xfalse, 32, 0, -1, -1, 0},
        {"cybdth", xfalse, 32, 0, -1, -1, 0},
        {"spidth", xfalse, 32, 0, -1, -1, 0},
        {"bspdth", xfalse, 32, 0, -1, -1, 0},
        {"vildth", xfalse, 32, 0, -1, -1, 0},
        {"kntdth", xfalse, 32, 0, -1, -1, 0},
        {"pedth", xfalse, 32, 0, -1, -1, 0},
        {"skedth", xfalse, 32, 0, -1, -1, 0},
        {"posact", xtrue, 120, 0, -1, -1, 0},
        {"bgact", xtrue, 120, 0, -1, -1, 0},
        {"dmact", xtrue, 120, 0, -1, -1, 0},
        {"bspact", xtrue, 100, 0, -1, -1, 0},
        {"bspwlk", xtrue, 100, 0, -1, -1, 0},
        {"vilact", xtrue, 100, 0, -1, -1, 0},
        {"noway", xfalse, 78, 0, -1, -1, 0},
        {"barexp", xfalse, 60, 0, -1, -1, 0},
        {"punch", xfalse, 64, 0, -1, -1, 0},
        {"hoof", xfalse, 70, 0, -1, -1, 0},
        {"metal", xfalse, 70, 0, -1, -1, 0},
        {"chgun", xfalse, 64, &S_sfx[sfx_pistol], 150, 0, 0},
        {"tink", xfalse, 60, 0, -1, -1, 0},
        {"bdopn", xfalse, 100, 0, -1, -1, 0},
        {"bdcls", xfalse, 100, 0, -1, -1, 0},
        {"itmbk", xfalse, 100, 0, -1, -1, 0},
        {"flame", xfalse, 32, 0, -1, -1, 0},
        {"flamst", xfalse, 32, 0, -1, -1, 0},
        {"getpow", xfalse, 60, 0, -1, -1, 0},
        {"bospit", xfalse, 70, 0, -1, -1, 0},
        {"boscub", xfalse, 70, 0, -1, -1, 0},
        {"bossit", xfalse, 70, 0, -1, -1, 0},
        {"bospn", xfalse, 70, 0, -1, -1, 0},
        {"bosdth", xfalse, 70, 0, -1, -1, 0},
        {"manatk", xfalse, 70, 0, -1, -1, 0},
        {"mandth", xfalse, 70, 0, -1, -1, 0},
        {"sssit", xfalse, 70, 0, -1, -1, 0},
        {"ssdth", xfalse, 70, 0, -1, -1, 0},
        {"keenpn", xfalse, 70, 0, -1, -1, 0},
        {"keendt", xfalse, 70, 0, -1, -1, 0},
        {"skeact", xfalse, 70, 0, -1, -1, 0},
        {"skesit", xfalse, 70, 0, -1, -1, 0},
        {"skeatk", xfalse, 70, 0, -1, -1, 0},
        {"radio", xfalse, 60, 0, -1, -1, 0},

#ifdef DOGS
        // killough 11/98: dog sounds
        { "dgsit",  false,   98, 0, -1, -1, 0 },
        { "dgatk",  false,   70, 0, -1, -1, 0 },
        { "dgact",  false,  120, 0, -1, -1, 0 },
        { "dgdth",  false,   70, 0, -1, -1, 0 },
        { "dgpain", xfalse,   96, 0, -1, -1, 0 },
#endif
};
