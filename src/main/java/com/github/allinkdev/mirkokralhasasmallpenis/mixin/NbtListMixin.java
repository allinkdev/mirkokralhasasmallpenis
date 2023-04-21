package com.github.allinkdev.mirkokralhasasmallpenis.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtTagSizeTracker;
import net.minecraft.nbt.NbtType;
import net.minecraft.nbt.scanner.NbtScanner;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.io.DataInput;
import java.io.IOException;

// Epic Method
@Environment(EnvType.CLIENT)
@Mixin(NbtList.class)
public final class NbtListMixin implements NbtType.OfVariableSize<NbtList> {
	private static NbtType<NbtList> actualImplementation;

	@Redirect(method = "<clinit>", at = @At(value = "FIELD", target = "Lnet/minecraft/nbt/NbtList;TYPE:Lnet/minecraft/nbt/NbtType;", opcode = Opcodes.PUTSTATIC))
	private static void ListTag$PUTSTATIC$TYPE(final NbtType<NbtList> value) {
		actualImplementation = value;
		NbtList.TYPE = new NbtListMixin();
	}

	@Override
	public NbtList read(DataInput input, int depth, NbtTagSizeTracker tracker) throws IOException {
		return actualImplementation.read(input, 0, NbtTagSizeTracker.EMPTY);
	}

	@Override
	public NbtScanner.Result doAccept(DataInput input, NbtScanner visitor) throws IOException {
		return actualImplementation.doAccept(input, visitor);
	}

	@Override
	public void skip(DataInput input) throws IOException {
		actualImplementation.skip(input);
	}

	@Override
	public String getCrashReportName() {
		return actualImplementation.getCrashReportName();
	}

	@Override
	public String getCommandFeedbackName() {
		return actualImplementation.getCommandFeedbackName();
	}
}
