package org.itmo.fuzzing.lect2.instrumentation;

import java.lang.classfile.ClassFile;
import java.lang.classfile.instruction.BranchInstruction;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

public class NewCoverageAgent {

    public static void premain(String agentArgs, Instrumentation inst) {

        inst.addTransformer(new ClassFileTransformer() {
            @Override
            public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
                                    ProtectionDomain protectionDomain, byte[] classfileBuffer) {
                var classModel = ClassFile.of().parse(classfileBuffer);
                var methods = classModel.methods();
                if (!className.contains("org/itmo/fuzzing/")) return classfileBuffer;
                for (var method : methods) {
                    if (!method.methodName().stringValue().contains("deleteRandomCharacter")) continue;
                    method.code().get().elementList().forEach(codeElement -> {
                        if (codeElement instanceof BranchInstruction i) {
                            System.out.println(STR."BRANCH \{i} \{i.target()} \{i.opcode()}");
                        }
                    });

                }
                return classfileBuffer;
            }
        });

    }
}
