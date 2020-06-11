package com.snow.nu;

import javassist.*;

import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;
import java.security.ProtectionDomain;

/**
 * created with IDEA
 *
 * @author:huqm
 * @date:2020/6/10
 * @time:14:23 <p>
 *
 * </p>
 */
public class AgentMain {

    public static void agentmain(String agentArgs, Instrumentation instrumentation) throws UnmodifiableClassException {
        instrumentation.addTransformer(new DefineTransformer(), true);
        instrumentation.retransformClasses(User.class);
        System.out.println("Agent Main Done");
    }

    static class DefineTransformer implements ClassFileTransformer {

        @Override
        public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
            System.out.println("agentmain - premain load Class:" + className);




                    System.out.println("执行自定义ClassFileTransformer"+loader);


                        // 从ClassPool获得CtClass对象
                        final ClassPool classPool = ClassPool.getDefault();
                        classPool.insertClassPath(new ClassClassPath(this.getClass()));
                        CtClass clazz = null;
                        try {
                            clazz = classPool.get("com.snow.nu.User");
                            if(clazz != null){
                                System.out.println("存在User");
                            }
                        } catch (NotFoundException e) {
                            e.printStackTrace();
                        }
                        CtMethod convertToAbbr = null;
                        try {
                            convertToAbbr = clazz.getDeclaredMethod("toString");
                        } catch (NotFoundException e) {
                            e.printStackTrace();
                        }
                        //这里对 java.util.Date.convertToAbbr() 方法进行了改写，在 return之前增加了一个 打印操作
                        String methodBody = "{return \"重写User.toString方法成功\";}";
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
    }
}
