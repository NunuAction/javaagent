package com.example.demo.controller;

import com.snow.nu.User;
import com.sun.tools.attach.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

/**
 * created with IDEA
 *
 * @author:huqm
 * @date:2020/6/10
 * @time:16:08 <p>
 *
 * </p>
 */
@RestController
@RequestMapping("test")
public class TestController {


    @RequestMapping("agent/main")
    public String test() throws IOException, AttachNotSupportedException, AgentLoadException, AgentInitializationException {
        //----------------AgentMain Start---------------------
        //获取当前系统中所有 运行中的 虚拟机
        User user =new User();
        user.setAge(22);
        user.setName("cat");
        System.out.println("running JVM start ");
        List<VirtualMachineDescriptor> list = VirtualMachine.list();
        for (VirtualMachineDescriptor vmd : list) {
            //如果虚拟机的名称为 xxx 则 该虚拟机为目标虚拟机，获取该虚拟机的 pid
            //然后加载 agent.jar 发送给该虚拟机
            System.out.println(vmd.displayName());
            if (vmd.displayName().endsWith("com.example.test.TestApplication")) {
                VirtualMachine virtualMachine = VirtualMachine.attach(vmd.id());
                System.out.println(user.toString());
                virtualMachine.loadAgent("D:\\project\\github\\nunu\\agent-main\\target\\agent-main-1.0-SNAPSHOT.jar");
                System.out.println(user.toString());
                virtualMachine.detach();
            }
        }
        return user.toString();
    }
}
