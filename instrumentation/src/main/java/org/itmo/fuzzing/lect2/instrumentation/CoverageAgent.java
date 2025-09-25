package org.itmo.fuzzing.lect2.instrumentation;

import org.objectweb.asm.*;
import java.lang.instrument.Instrumentation;
import java.lang.instrument.ClassFileTransformer;
import java.security.ProtectionDomain;

public class CoverageAgent {

    public static void premain(String agentArgs, Instrumentation inst) {

        inst.addTransformer(new ClassFileTransformer() {
            @Override
            public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
                                    ProtectionDomain protectionDomain, byte[] classfileBuffer) {
                if (className.contains("exp4j") && !className.contains("CoverageAgent")) {
                    return asmTransformClass(className, classfileBuffer);
//                    return asmTransformClass(className, classfileBuffer);
                }
                return null;
            }
        });

    }

//    private static byte[] asmTransformClass(String className, byte[] classfileBuffer) {
//        ClassReader cr = new ClassReader(classfileBuffer);
//        ClassWriter cw = new ClassWriter(cr, ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
//        ClassVisitor cv = new ClassVisitor(Opcodes.ASM9, cw) {
//            @Override
//            public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
//                MethodVisitor mv = super.visitMethod(access, name, descriptor, signature, exceptions);
//                return new MethodVisitor(Opcodes.ASM9, mv) {
//
//                    @Override
//                    public void visitLineNumber(int line, Label start) {
//                        super.visitLineNumber(line, start);
//                        CoverageTracker.logFullCoverage(name, Integer.toString(line));
//                        mv.visitLdcInsn(name);
//                        mv.visitLdcInsn(Integer.toString(line));
//                        mv.visitMethodInsn(Opcodes.INVOKESTATIC, "org/itmo/fuzzing/lect2/instrumentation/CoverageTracker", "logCoverage", "(Ljava/lang/String;Ljava/lang/String;)V", false);
//                    }
//
//                };
//            }
//        };
//        cr.accept(cv, 0);
//        return cw.toByteArray();
//    }


    private static byte[] asmTransformClass(String className, byte[] classfileBuffer) {
        ClassReader cr = new ClassReader(classfileBuffer);
        ClassWriter cw = new ClassWriter(cr, ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
        ClassVisitor cv = new ClassVisitor(Opcodes.ASM9, cw) {
            @Override
            public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
                MethodVisitor mv = super.visitMethod(access, name, descriptor, signature, exceptions);
                return new MethodVisitor(Opcodes.ASM9, mv) {

                    private boolean hasInstructionAfterLabel = false;
                    private int lastLine = -1;
                    private Label lastLabel = null;

                    @Override
                    public void visitLineNumber(int line, Label start) {
                        super.visitLineNumber(line, start);
                        lastLine = line;
                        lastLabel = start;
                        hasInstructionAfterLabel = false;
                    }

                    private void instrumentIfNeeded() {
                        if (lastLine != -1 && !hasInstructionAfterLabel) {
                            hasInstructionAfterLabel = true;
                            CoverageTracker.logFullCoverage(name, Integer.toString(lastLine));
                            mv.visitLdcInsn(name);
                            mv.visitLdcInsn(Integer.toString(lastLine));
                            mv.visitMethodInsn(Opcodes.INVOKESTATIC,
                                    "org/itmo/fuzzing/lect2/instrumentation/CoverageTracker",
                                    "logCoverage", "(Ljava/lang/String;Ljava/lang/String;)V", false);
                        }
                    }

                    // Инструментируем перед любой "значимой" инструкцией
                    @Override
                    public void visitInsn(int opcode) {
                        // Пропускаем NOP и подобные незначимые инструкции
                        if (opcode != Opcodes.NOP) {
                            instrumentIfNeeded();
                        }
                        super.visitInsn(opcode);
                    }

                    @Override
                    public void visitVarInsn(int opcode, int var) {
                        instrumentIfNeeded();
                        super.visitVarInsn(opcode, var);
                    }

                    @Override
                    public void visitFieldInsn(int opcode, String owner, String name, String descriptor) {
                        instrumentIfNeeded();
                        super.visitFieldInsn(opcode, owner, name, descriptor);
                    }

                    @Override
                    public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {
                        instrumentIfNeeded();
                        super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
                    }

                    @Override
                    public void visitJumpInsn(int opcode, Label label) {
                        instrumentIfNeeded();
                        super.visitJumpInsn(opcode, label);
                    }

                    @Override
                    public void visitTypeInsn(int opcode, String type) {
                        instrumentIfNeeded();
                        super.visitTypeInsn(opcode, type);
                    }

                    @Override
                    public void visitLdcInsn(Object value) {
                        instrumentIfNeeded();
                        super.visitLdcInsn(value);
                    }
                };
            }
        };
        cr.accept(cv, 0);
        return cw.toByteArray();
    }
}
