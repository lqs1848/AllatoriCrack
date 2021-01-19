package net.moofMonkey.eye;

import java.lang.instrument.Instrumentation;

import net.moofMonkey.eye.modules.ClassTransformer;

public class Main extends Thread {
	public static void premain(String args, Instrumentation inst) throws Throwable {
		ClassTransformer.pool.importPackage("net.moofMonkey.eye.modules");
		
		inst.addTransformer(new ClassTransformer());

		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				try {
					ClassTransformer.saveAll();
				} catch (Throwable e) {
					e.printStackTrace();
				}
			}
		});
	}
}
