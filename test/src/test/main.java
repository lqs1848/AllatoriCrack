package test;

import java.lang.instrument.Instrumentation;
import java.lang.reflect.Method;

import com.allatori.StackTrace;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.CtNewMethod;
import net.moofMonkey.eye.modules.ClassTransformer;

public class main {
	 
	  public static void main(String[] args) throws Throwable {
		 // ClassTransformer.pool.importPackage("net.moofMonkey.eye.modules.ClassNameGen");
		  ClassTransformer.saveAll();
	  }

}
