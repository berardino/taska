import * as jspb from "google-protobuf";

import * as google_protobuf_wrappers_pb from "google-protobuf/google/protobuf/wrappers_pb";

export class CreateListReq extends jspb.Message {
  getBoardId(): string;
  setBoardId(value: string): CreateListReq;

  getTitle(): string;
  setTitle(value: string): CreateListReq;

  getDescription(): google_protobuf_wrappers_pb.StringValue | undefined;
  setDescription(
    value?: google_protobuf_wrappers_pb.StringValue
  ): CreateListReq;
  hasDescription(): boolean;
  clearDescription(): CreateListReq;

  serializeBinary(): Uint8Array;
  toObject(includeInstance?: boolean): CreateListReq.AsObject;
  static toObject(
    includeInstance: boolean,
    msg: CreateListReq
  ): CreateListReq.AsObject;
  static serializeBinaryToWriter(
    message: CreateListReq,
    writer: jspb.BinaryWriter
  ): void;
  static deserializeBinary(bytes: Uint8Array): CreateListReq;
  static deserializeBinaryFromReader(
    message: CreateListReq,
    reader: jspb.BinaryReader
  ): CreateListReq;
}

export namespace CreateListReq {
  export type AsObject = {
    boardId: string;
    title: string;
    description?: google_protobuf_wrappers_pb.StringValue.AsObject;
  };
}

export class CreateListRes extends jspb.Message {
  getId(): string;
  setId(value: string): CreateListRes;

  serializeBinary(): Uint8Array;
  toObject(includeInstance?: boolean): CreateListRes.AsObject;
  static toObject(
    includeInstance: boolean,
    msg: CreateListRes
  ): CreateListRes.AsObject;
  static serializeBinaryToWriter(
    message: CreateListRes,
    writer: jspb.BinaryWriter
  ): void;
  static deserializeBinary(bytes: Uint8Array): CreateListRes;
  static deserializeBinaryFromReader(
    message: CreateListRes,
    reader: jspb.BinaryReader
  ): CreateListRes;
}

export namespace CreateListRes {
  export type AsObject = {
    id: string;
  };
}

export class GetListReq extends jspb.Message {
  getId(): string;
  setId(value: string): GetListReq;

  serializeBinary(): Uint8Array;
  toObject(includeInstance?: boolean): GetListReq.AsObject;
  static toObject(
    includeInstance: boolean,
    msg: GetListReq
  ): GetListReq.AsObject;
  static serializeBinaryToWriter(
    message: GetListReq,
    writer: jspb.BinaryWriter
  ): void;
  static deserializeBinary(bytes: Uint8Array): GetListReq;
  static deserializeBinaryFromReader(
    message: GetListReq,
    reader: jspb.BinaryReader
  ): GetListReq;
}

export namespace GetListReq {
  export type AsObject = {
    id: string;
  };
}

export class GetListRes extends jspb.Message {
  getBoardId(): string;
  setBoardId(value: string): GetListRes;

  getTitle(): string;
  setTitle(value: string): GetListRes;

  getDescription(): google_protobuf_wrappers_pb.StringValue | undefined;
  setDescription(value?: google_protobuf_wrappers_pb.StringValue): GetListRes;
  hasDescription(): boolean;
  clearDescription(): GetListRes;

  serializeBinary(): Uint8Array;
  toObject(includeInstance?: boolean): GetListRes.AsObject;
  static toObject(
    includeInstance: boolean,
    msg: GetListRes
  ): GetListRes.AsObject;
  static serializeBinaryToWriter(
    message: GetListRes,
    writer: jspb.BinaryWriter
  ): void;
  static deserializeBinary(bytes: Uint8Array): GetListRes;
  static deserializeBinaryFromReader(
    message: GetListRes,
    reader: jspb.BinaryReader
  ): GetListRes;
}

export namespace GetListRes {
  export type AsObject = {
    boardId: string;
    title: string;
    description?: google_protobuf_wrappers_pb.StringValue.AsObject;
  };
}

export class ArchiveListReq extends jspb.Message {
  getId(): string;
  setId(value: string): ArchiveListReq;

  serializeBinary(): Uint8Array;
  toObject(includeInstance?: boolean): ArchiveListReq.AsObject;
  static toObject(
    includeInstance: boolean,
    msg: ArchiveListReq
  ): ArchiveListReq.AsObject;
  static serializeBinaryToWriter(
    message: ArchiveListReq,
    writer: jspb.BinaryWriter
  ): void;
  static deserializeBinary(bytes: Uint8Array): ArchiveListReq;
  static deserializeBinaryFromReader(
    message: ArchiveListReq,
    reader: jspb.BinaryReader
  ): ArchiveListReq;
}

export namespace ArchiveListReq {
  export type AsObject = {
    id: string;
  };
}

export class ArchiveListRes extends jspb.Message {
  serializeBinary(): Uint8Array;
  toObject(includeInstance?: boolean): ArchiveListRes.AsObject;
  static toObject(
    includeInstance: boolean,
    msg: ArchiveListRes
  ): ArchiveListRes.AsObject;
  static serializeBinaryToWriter(
    message: ArchiveListRes,
    writer: jspb.BinaryWriter
  ): void;
  static deserializeBinary(bytes: Uint8Array): ArchiveListRes;
  static deserializeBinaryFromReader(
    message: ArchiveListRes,
    reader: jspb.BinaryReader
  ): ArchiveListRes;
}

export namespace ArchiveListRes {
  export type AsObject = {};
}

export class UnArchiveListReq extends jspb.Message {
  getId(): string;
  setId(value: string): UnArchiveListReq;

  serializeBinary(): Uint8Array;
  toObject(includeInstance?: boolean): UnArchiveListReq.AsObject;
  static toObject(
    includeInstance: boolean,
    msg: UnArchiveListReq
  ): UnArchiveListReq.AsObject;
  static serializeBinaryToWriter(
    message: UnArchiveListReq,
    writer: jspb.BinaryWriter
  ): void;
  static deserializeBinary(bytes: Uint8Array): UnArchiveListReq;
  static deserializeBinaryFromReader(
    message: UnArchiveListReq,
    reader: jspb.BinaryReader
  ): UnArchiveListReq;
}

export namespace UnArchiveListReq {
  export type AsObject = {
    id: string;
  };
}

export class UnArchiveListRes extends jspb.Message {
  serializeBinary(): Uint8Array;
  toObject(includeInstance?: boolean): UnArchiveListRes.AsObject;
  static toObject(
    includeInstance: boolean,
    msg: UnArchiveListRes
  ): UnArchiveListRes.AsObject;
  static serializeBinaryToWriter(
    message: UnArchiveListRes,
    writer: jspb.BinaryWriter
  ): void;
  static deserializeBinary(bytes: Uint8Array): UnArchiveListRes;
  static deserializeBinaryFromReader(
    message: UnArchiveListRes,
    reader: jspb.BinaryReader
  ): UnArchiveListRes;
}

export namespace UnArchiveListRes {
  export type AsObject = {};
}

export class UpdateListReq extends jspb.Message {
  getId(): string;
  setId(value: string): UpdateListReq;

  getUpdatesList(): Array<UpdateListReq.Update>;
  setUpdatesList(value: Array<UpdateListReq.Update>): UpdateListReq;
  clearUpdatesList(): UpdateListReq;
  addUpdates(
    value?: UpdateListReq.Update,
    index?: number
  ): UpdateListReq.Update;

  serializeBinary(): Uint8Array;
  toObject(includeInstance?: boolean): UpdateListReq.AsObject;
  static toObject(
    includeInstance: boolean,
    msg: UpdateListReq
  ): UpdateListReq.AsObject;
  static serializeBinaryToWriter(
    message: UpdateListReq,
    writer: jspb.BinaryWriter
  ): void;
  static deserializeBinary(bytes: Uint8Array): UpdateListReq;
  static deserializeBinaryFromReader(
    message: UpdateListReq,
    reader: jspb.BinaryReader
  ): UpdateListReq;
}

export namespace UpdateListReq {
  export type AsObject = {
    id: string;
    updatesList: Array<UpdateListReq.Update.AsObject>;
  };

  export class Update extends jspb.Message {
    getPUpdateListTitleCmd(): UpdateListReq.PUpdateListTitleOp | undefined;
    setPUpdateListTitleCmd(value?: UpdateListReq.PUpdateListTitleOp): Update;
    hasPUpdateListTitleCmd(): boolean;
    clearPUpdateListTitleCmd(): Update;

    getCmdCase(): Update.CmdCase;

    serializeBinary(): Uint8Array;
    toObject(includeInstance?: boolean): Update.AsObject;
    static toObject(includeInstance: boolean, msg: Update): Update.AsObject;
    static serializeBinaryToWriter(
      message: Update,
      writer: jspb.BinaryWriter
    ): void;
    static deserializeBinary(bytes: Uint8Array): Update;
    static deserializeBinaryFromReader(
      message: Update,
      reader: jspb.BinaryReader
    ): Update;
  }

  export namespace Update {
    export type AsObject = {
      pUpdateListTitleCmd?: UpdateListReq.PUpdateListTitleOp.AsObject;
    };

    export enum CmdCase {
      CMD_NOT_SET = 0,
      P_UPDATE_LIST_TITLE_CMD = 1,
    }
  }

  export class PUpdateListTitleOp extends jspb.Message {
    getTitle(): string;
    setTitle(value: string): PUpdateListTitleOp;

    serializeBinary(): Uint8Array;
    toObject(includeInstance?: boolean): PUpdateListTitleOp.AsObject;
    static toObject(
      includeInstance: boolean,
      msg: PUpdateListTitleOp
    ): PUpdateListTitleOp.AsObject;
    static serializeBinaryToWriter(
      message: PUpdateListTitleOp,
      writer: jspb.BinaryWriter
    ): void;
    static deserializeBinary(bytes: Uint8Array): PUpdateListTitleOp;
    static deserializeBinaryFromReader(
      message: PUpdateListTitleOp,
      reader: jspb.BinaryReader
    ): PUpdateListTitleOp;
  }

  export namespace PUpdateListTitleOp {
    export type AsObject = {
      title: string;
    };
  }
}

export class UpdateListRes extends jspb.Message {
  serializeBinary(): Uint8Array;
  toObject(includeInstance?: boolean): UpdateListRes.AsObject;
  static toObject(
    includeInstance: boolean,
    msg: UpdateListRes
  ): UpdateListRes.AsObject;
  static serializeBinaryToWriter(
    message: UpdateListRes,
    writer: jspb.BinaryWriter
  ): void;
  static deserializeBinary(bytes: Uint8Array): UpdateListRes;
  static deserializeBinaryFromReader(
    message: UpdateListRes,
    reader: jspb.BinaryReader
  ): UpdateListRes;
}

export namespace UpdateListRes {
  export type AsObject = {};
}
