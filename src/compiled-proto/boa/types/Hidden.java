// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: hidden.proto

package boa.types;

public final class Hidden {
  private Hidden() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registry.add(boa.types.Hidden.hidden);
    registry.add(boa.types.Hidden.kindFunc);
  }
  public static final int HIDDEN_FIELD_NUMBER = 50000;
  /**
   * <code>extend .google.protobuf.FieldOptions { ... }</code>
   */
  public static final
    com.google.protobuf.GeneratedMessage.GeneratedExtension<
      com.google.protobuf.DescriptorProtos.FieldOptions,
      java.lang.Boolean> hidden = com.google.protobuf.GeneratedMessage
          .newFileScopedGeneratedExtension(
        java.lang.Boolean.class,
        null);
  public static final int KIND_FUNC_FIELD_NUMBER = 50001;
  /**
   * <code>extend .google.protobuf.MessageOptions { ... }</code>
   */
  public static final
    com.google.protobuf.GeneratedMessage.GeneratedExtension<
      com.google.protobuf.DescriptorProtos.MessageOptions,
      java.lang.String> kindFunc = com.google.protobuf.GeneratedMessage
          .newFileScopedGeneratedExtension(
        java.lang.String.class,
        null);

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\014hidden.proto\022\tboa.types\032 google/protob" +
      "uf/descriptor.proto:6\n\006hidden\022\035.google.p" +
      "rotobuf.FieldOptions\030\320\206\003 \001(\010:\005false:6\n\tk" +
      "ind_func\022\037.google.protobuf.MessageOption" +
      "s\030\321\206\003 \001(\t:\000B\002H\001"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
      new com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner() {
        public com.google.protobuf.ExtensionRegistry assignDescriptors(
            com.google.protobuf.Descriptors.FileDescriptor root) {
          descriptor = root;
          hidden.internalInit(descriptor.getExtensions().get(0));
          kindFunc.internalInit(descriptor.getExtensions().get(1));
          return null;
        }
      };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
          com.google.protobuf.DescriptorProtos.getDescriptor(),
        }, assigner);
  }

  // @@protoc_insertion_point(outer_class_scope)
}
