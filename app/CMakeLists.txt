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

SET (CMAKE_C_FLAGS  "${CMAKE_C_FLAGS} -DLINUX -DANDROID -DANDROID_STUDIO -DSHARED_LIBRARY -fPIC -DNORMALINUX -DLINUX -DHAVE_NET -DUSE_SDL_NET -DHAVE_CONFIG_H -falign-functions=16 -Wno-implicit-function-declaration -Wno-implicit-int -Wno-return-type -Wno-pointer-sign -Wno-empty-body -Wno-comment -W-notautological-constant-out-of-range-compare -Wno-incompatible-pointer-types -Wno-format -Wno-absolute-value -Wno-pointer-to-int-cast -Wno-deprecated-non-prototype"  )
SET (CMAKE_C_FLAGS  "${CMAKE_C_FLAGS} ${GCC_COMPILATION_FLAGS}" )


set (lib-doom "peterpan.doom")
set (dir-doom "")

add_library( ${lib-doom}
        SHARED
        ${c-files}${dir-doom}/am_map.c
        ${c-files}${dir-doom}/m_cheat.c
        ${c-files}${dir-doom}/p_lights.c
        ${c-files}${dir-doom}/p_user.c
        ${c-files}${dir-doom}/sounds.c
        ${c-files}${dir-doom}/hu_lib.c
        ${c-files}${dir-doom}/md5.c
        ${c-files}${dir-doom}/p_map.c
        ${c-files}${dir-doom}/r_bsp.c
        ${c-files}${dir-doom}/s_sound.c
        ${c-files}${dir-doom}/d_deh.c
        ${c-files}${dir-doom}/hu_stuff.c
        ${c-files}${dir-doom}/m_menu.c
        ${c-files}${dir-doom}/p_maputl.c
        ${c-files}${dir-doom}/r_data.c
        ${c-files}${dir-doom}/st_lib.c
        ${c-files}${dir-doom}/d_items.c
        ${c-files}${dir-doom}/i_main.c
        ${c-files}${dir-doom}/m_misc.c
        ${c-files}${dir-doom}/p_mobj.c
        ${c-files}${dir-doom}/r_demo.c
        ${c-files}${dir-doom}/st_stuff.c
        ${c-files}${dir-doom}/d_main.c
        ${c-files}${dir-doom}/info.c
        ${c-files}${dir-doom}/p_plats.c
        ${c-files}${dir-doom}/r_draw.c
        ${c-files}${dir-doom}/tables.c
        ${c-files}${dir-doom}/doomdef.c
        ${c-files}${dir-doom}/i_sound.c
        ${c-files}${dir-doom}/m_random.c
        ${c-files}${dir-doom}/p_pspr.c
        ${c-files}${dir-doom}/r_filter.c
        ${c-files}${dir-doom}/version.c
        ${c-files}${dir-doom}/doomstat.c
        ${c-files}${dir-doom}/i_system.c
        ${c-files}${dir-doom}/p_ceilng.c
        ${c-files}${dir-doom}/p_saveg.c
        ${c-files}${dir-doom}/r_fps.c
        ${c-files}${dir-doom}/v_video.c
        ${c-files}${dir-doom}/p_checksum.c
        ${c-files}${dir-doom}/p_setup.c
        ${c-files}${dir-doom}/r_main.c
        ${c-files}${dir-doom}/wi_stuff.c
        ${c-files}${dir-doom}/dstrings.c
        ${c-files}${dir-doom}/p_doors.c
        ${c-files}${dir-doom}/p_sight.c
        ${c-files}${dir-doom}/r_patch.c
        ${c-files}${dir-doom}/w_memcache.c
        ${c-files}${dir-doom}/f_finale.c
        ${c-files}${dir-doom}/jni_doom.c
        ${c-files}${dir-doom}/p_enemy.c
        ${c-files}${dir-doom}/p_spec.c
        ${c-files}${dir-doom}/r_plane.c
        ${c-files}${dir-doom}/w_mmap.c
        ${c-files}${dir-doom}/f_wipe.c
        ${c-files}${dir-doom}/lprintf.c
        ${c-files}${dir-doom}/p_floor.c
        ${c-files}${dir-doom}/p_switch.c
        ${c-files}${dir-doom}/r_segs.c
        ${c-files}${dir-doom}/w_wad.c
        ${c-files}${dir-doom}/g_game.c
        ${c-files}${dir-doom}/m_argv.c
        ${c-files}${dir-doom}/p_genlin.c
        ${c-files}${dir-doom}/p_telept.c
        ${c-files}${dir-doom}/r_sky.c
        ${c-files}${dir-doom}/z_bmalloc.c
        ${c-files}${dir-doom}/m_bbox.c
        ${c-files}${dir-doom}/p_inter.c
        ${c-files}${dir-doom}/p_tick.c
        ${c-files}${dir-doom}/r_things.c
        ${c-files}${dir-doom}/z_zone.c
        ${c-files}${dir-doom}/d_client.c
        ${c-files}${dir-doom}/i_video.c
        ${c-files}${dir-doom}/i_network.c
        ${c-files}${dir-doom}/d_server.c
)

target_link_libraries(${lib-doom} ${log-lib} )
target_link_libraries(${lib-doom} ${m-lib} )
target_link_options(${lib-doom} PRIVATE "-Wl,-z,max-page-size=16384")

