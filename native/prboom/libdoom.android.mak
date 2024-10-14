# NOTE: This version of Adventure was developed by the author on private
# equipment, and has been ported to Android by Luiz Valdetaro for entertainment purposes only.
# The author (Don Woods) retains full rights to the work.

# This make file is intended for use with Windows as the cross-compile host system.
# To compile this you must install the android NDK, and must modify the window's PATH environment variable. As an example, this is what I did:
# set path=c:\android-ndk\toolchains\arm-linux-androideabi-4.4.3\prebuilt\windows\bin;%path%
# set path=c:\android-sdk\platform-tools;%path%

# NDK should point to the root of the Android NDK
NDK=\android-ndk

COMP=arm-linux-androideabi-gcc
LINK=arm-linux-androideabi-ld
COPY=cp

# SYSROOT points to the top of include directories - in this case for android API 19
SYSROOT=/android-ndk/platforms/android-19/arch-arm
LIB=$(SYSROOT)/usr/lib


OBJS 	= \
 am_map.o    m_cheat.o     p_lights.o  p_user.o    sounds.o \
 hu_lib.o       md5.o         p_map.o     r_bsp.o     s_sound.o \
 d_deh.o     hu_stuff.o     m_menu.o      p_maputl.o  r_data.o    st_lib.o \
 d_items.o   i_main.o       m_misc.o      p_mobj.o    r_demo.o    st_stuff.o \
 d_main.o    info.o             p_plats.o   r_draw.o    tables.o \
 doomdef.o   i_sound.o      m_random.o    p_pspr.o    r_filter.o  version.o \
 doomstat.o  i_system.o     p_ceilng.o    p_saveg.o   r_fps.o     v_video.o \
 p_checksum.o  p_setup.o   r_main.o    wi_stuff.o \
 dstrings.o   p_doors.o     p_sight.o   r_patch.o   w_memcache.o \
 f_finale.o  jni_doom.o     p_enemy.o     p_spec.o    r_plane.o   w_mmap.o \
 f_wipe.o    lprintf.o      p_floor.o     p_switch.o  r_segs.o    w_wad.o \
 g_game.o    m_argv.o       p_genlin.o    p_telept.o  r_sky.o     z_bmalloc.o \
 m_bbox.o       p_inter.o     p_tick.o    r_things.o  z_zone.o \
 d_client.o  i_video.o i_network.o d_server.o


.c.o:
	$(COMP) -g -c --sysroot=$(SYSROOT) -fPIC -DNORMALINUX -DLINUX -DHAVE_NET -DUSE_SDL_NET -DHAVE_CONFIG_H $<

libdoom_jni.so:	$(OBJS)
	$(LINK) -shared -o libdoom_jni.so --dynamic-linker=/system/bin/linker -nostdlib -rpath /system/lib -L. -rpath $(SYSROOT)/lib -L$(SYSROOT)/lib -rpath $(LIB) -L $(LIB) -lc -lm -ldl -lz $(OBJS)
	$(COPY) libdoom_jni.so ../../libs/armeabi



doom.jni_Natives.h: ../../bin/classes/doom/jni/Natives.class
	javah -jni -classpath ../../bin/classes -d . doom.doom.jni.Natives


