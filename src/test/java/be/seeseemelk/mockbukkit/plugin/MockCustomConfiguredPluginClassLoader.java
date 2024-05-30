package be.seeseemelk.mockbukkit.plugin;

import io.papermc.paper.plugin.configuration.PluginMeta;
import io.papermc.paper.plugin.provider.classloader.ConfiguredPluginClassLoader;
import io.papermc.paper.plugin.provider.classloader.PluginClassLoaderGroup;
import net.bytebuddy.jar.asm.ClassWriter;
import net.bytebuddy.jar.asm.MethodVisitor;
import net.bytebuddy.jar.asm.Opcodes;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;

public class MockCustomConfiguredPluginClassLoader extends ClassLoader implements ConfiguredPluginClassLoader
{

	private final ConfiguredPluginClassLoader parent;

	public MockCustomConfiguredPluginClassLoader(ConfiguredPluginClassLoader parent)
	{
		super();
		this.parent = parent;
	}

	@Override
	public PluginMeta getConfiguration()
	{
		return parent.getConfiguration();
	}

	@Override
	public Class<?> loadClass(@NotNull String name, boolean resolve, boolean checkGlobal, boolean checkLibraries)
			throws ClassNotFoundException
	{
		return loadClass(name, resolve);
	}

	@Override
	public void init(JavaPlugin plugin)
	{
		parent.init(plugin);
	}

	@Override
	public @Nullable JavaPlugin getPlugin()
	{
		return parent.getPlugin();
	}

	@Override
	public @Nullable PluginClassLoaderGroup getGroup()
	{
		return parent.getGroup();
	}

	@Override
	public void close() throws IOException
	{

	}

	public void createCustomClass()
	{
		ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
		cw.visit(52, Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "TestClass", null, "java/lang/Object", null);
		MethodVisitor mv = cw.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_STATIC, "testMethod",
				"(Lbe/seeseemelk/mockbukkit/TestPlugin;)V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitInsn(Opcodes.ICONST_1);
		mv.visitFieldInsn(Opcodes.PUTFIELD, "be/seeseemelk/mockbukkit/TestPlugin", "classLoadSucceed", "Z");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(2, 2);
		mv.visitEnd();
		cw.visitEnd();

		byte[] classBytes = cw.toByteArray();
		this.defineClass("TestClass", classBytes, 0, classBytes.length);
	}

}
