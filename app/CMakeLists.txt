# Sets the minimum version of CMake required to build your native library.
# This ensures that a certain set of CMake features is available to
# your build.

cmake_minimum_required(VERSION 3.4.1)

set (base-dir ${PROJECT_SOURCE_DIR})
set (lib-files "/src/main/cpp/")
set (c-files "src/main/cpp/")

#include directories
include_directories(${c-files})


#dynamic libraries to be used
find_library( log-lib log )
find_library( m-lib m )
find_library( z-lib z )

SET (CMAKE_C_FLAGS  "${CMAKE_C_FLAGS} -DLINUX -DANDROID -DANDROID_STUDIO -DSHARED_LIBRARY -fPIC -DNORMALINUX -DLINUX -DHAVE_NET -DUSE_SDL_NET -DHAVE_CONFIG_H -Wno-implicit-function-declaration -Wno-implicit-int -Wno-return-type -Wno-pointer-sign -Wno-empty-body -Wno-comment -W-notautological-constant-out-of-range-compare -Wno-incompatible-pointer-types -Wno-format -Wno-absolute-value -Wno-pointer-to-int-cast -Wno-deprecated-non-prototype"  )
SET (CMAKE_C_FLAGS  "${CMAKE_C_FLAGS} ${GCC_COMPILATION_FLAGS}" )


set (lib-adv "peterpan.doom")
set (dir-adv "")

add_library( ${lib-adv}
        SHARED
        ${c-files}${dir-adv}/am_map.c
        ${c-files}${dir-adv}/m_cheat.c
        ${c-files}${dir-adv}/p_lights.c
        ${c-files}${dir-adv}/p_user.c
        ${c-files}${dir-adv}/sounds.c
        ${c-files}${dir-adv}/hu_lib.c
        ${c-files}${dir-adv}/md5.c
        ${c-files}${dir-adv}/p_map.c
        ${c-files}${dir-adv}/r_bsp.c
        ${c-files}${dir-adv}/s_sound.c
        ${c-files}${dir-adv}/d_deh.c
        ${c-files}${dir-adv}/hu_stuff.c
        ${c-files}${dir-adv}/m_menu.c
        ${c-files}${dir-adv}/p_maputl.c
        ${c-files}${dir-adv}/r_data.c
        ${c-files}${dir-adv}/st_lib.c
        ${c-files}${dir-adv}/d_items.c
        ${c-files}${dir-adv}/i_main.c
        ${c-files}${dir-adv}/m_misc.c
        ${c-files}${dir-adv}/p_mobj.c
        ${c-files}${dir-adv}/r_demo.c
        ${c-files}${dir-adv}/st_stuff.c
        ${c-files}${dir-adv}/d_main.c
        ${c-files}${dir-adv}/info.c
        ${c-files}${dir-adv}/p_plats.c
        ${c-files}${dir-adv}/r_draw.c
        ${c-files}${dir-adv}/tables.c
        ${c-files}${dir-adv}/doomdef.c
        ${c-files}${dir-adv}/i_sound.c
        ${c-files}${dir-adv}/m_random.c
        ${c-files}${dir-adv}/p_pspr.c
        ${c-files}${dir-adv}/r_filter.c
        ${c-files}${dir-adv}/version.c
        ${c-files}${dir-adv}/doomstat.c
        ${c-files}${dir-adv}/i_system.c
        ${c-files}${dir-adv}/p_ceilng.c
        ${c-files}${dir-adv}/p_saveg.c
        ${c-files}${dir-adv}/r_fps.c
        ${c-files}${dir-adv}/v_video.c
        ${c-files}${dir-adv}/p_checksum.c
        ${c-files}${dir-adv}/p_setup.c
        ${c-files}${dir-adv}/r_main.c
        ${c-files}${dir-adv}/wi_stuff.c
        ${c-files}${dir-adv}/dstrings.c
        ${c-files}${dir-adv}/p_doors.c
        ${c-files}${dir-adv}/p_sight.c
        ${c-files}${dir-adv}/r_patch.c
        ${c-files}${dir-adv}/w_memcache.c
        ${c-files}${dir-adv}/f_finale.c
        ${c-files}${dir-adv}/jni_doom.c
        ${c-files}${dir-adv}/p_enemy.c
        ${c-files}${dir-adv}/p_spec.c
        ${c-files}${dir-adv}/r_plane.c
        ${c-files}${dir-adv}/w_mmap.c
        ${c-files}${dir-adv}/f_wipe.c
        ${c-files}${dir-adv}/lprintf.c
        ${c-files}${dir-adv}/p_floor.c
        ${c-files}${dir-adv}/p_switch.c
        ${c-files}${dir-adv}/r_segs.c
        ${c-files}${dir-adv}/w_wad.c
        ${c-files}${dir-adv}/g_game.c
        ${c-files}${dir-adv}/m_argv.c
        ${c-files}${dir-adv}/p_genlin.c
        ${c-files}${dir-adv}/p_telept.c
        ${c-files}${dir-adv}/r_sky.c
        ${c-files}${dir-adv}/z_bmalloc.c
        ${c-files}${dir-adv}/m_bbox.c
        ${c-files}${dir-adv}/p_inter.c
        ${c-files}${dir-adv}/p_tick.c
        ${c-files}${dir-adv}/r_things.c
        ${c-files}${dir-adv}/z_zone.c
        ${c-files}${dir-adv}/d_client.c
        ${c-files}${dir-adv}/i_video.c
        ${c-files}${dir-adv}/i_network.c
        ${c-files}${dir-adv}/d_server.c
)

target_link_libraries(${lib-adv} ${log-lib} )
target_link_libraries(${lib-adv} ${m-lib} )

