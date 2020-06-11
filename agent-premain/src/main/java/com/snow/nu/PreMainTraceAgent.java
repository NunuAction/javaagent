package com.snow.nu;

/**
 * created with IDEA
 *
 * @author:huqm
 * @date:2020/6/10
 * @time:10:30 <p>
 *
 * </p>
 */


import javassist.*;

import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

public class PreMainTraceAgent {

    public static void premain(String agentArgs, Instrumentation inst) {
        System.out.println("agentArgs : " + agentArgs);
        inst.addTransformer(new DefineTransformer(), true);
//        inst.addTransformer(new MyClassTransformer(),true);
//        inst.addTransformer(new MyClassTransformer(),true);
    }

    static class DefineTransformer implements ClassFileTransformer{

        @Override
        public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
            System.out.println("premain load Class:" + className);
            return classfileBuffer;
        }
    }
    static class MyClassTransformer implements ClassFileTransformer {
        @Override
        public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
            System.out.println("执行自定义ClassFileTransformer");
            if ("".equals(className)) {

                // 从ClassPool获得CtClass对象
                final ClassPool classPool = ClassPool.getDefault();
                CtClass clazz = null;
                try {
                    clazz = classPool.get("");
                } catch (NotFoundException e) {
                    e.printStackTrace();
                }
                CtMethod convertToAbbr = null;
                try {
                    convertToAbbr = clazz.getDeclaredMethod("startsWith");
                } catch (NotFoundException e) {
                    e.printStackTrace();
                }
                //这里对 java.util.Date.convertToAbbr() 方法进行了改写，在 return之前增加了一个 打印操作
                String methodBody = "{return startsWith(prefix, 1);}";
                try {
                    convertToAbbr.setBody(methodBody);
                } catch (CannotCompileException e) {
                    e.printStackTrace();
                }

                // 返回字节码，并且detachCtClass对象
                byte[] byteCode = new byte[0];
                try {
                    byteCode = clazz.toBytecode();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (CannotCompileException e) {
                    e.printStackTrace();
                }
                //detach的意思是将内存中曾经被javassist加载过的Date对象移除，如果下次有需要在内存中找不到会重新走javassist加载
                clazz.detach();
                return byteCode;

            }
            return null;
        }
    }

}

