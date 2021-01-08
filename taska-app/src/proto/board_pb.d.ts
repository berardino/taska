import * as jspb from "google-protobuf";

import * as google_protobuf_wrappers_pb from "google-protobuf/google/protobuf/wrappers_pb";

export class CreateBoardReq extends jspb.Message {
  getTitle(): string;
  setTitle(value: string): CreateBoardReq;

  getDescription(): google_protobuf_wrappers_pb.StringValue | undefined;
  setDescription(
    value?: google_protobuf_wrappers_pb.StringValue
  ): CreateBoardReq;
  hasDescription(): boolean;
  clearDescription(): CreateBoardReq;

  serializeBinary(): Uint8Array;
  toObject(includeInstance?: boolean): CreateBoardReq.AsObject;
  static toObject(
    includeInstance: boolean,
    msg: CreateBoardReq
  ): CreateBoardReq.AsObject;
  static serializeBinaryToWriter(
    message: CreateBoardReq,
    writer: jspb.BinaryWriter
  ): void;
  static deserializeBinary(bytes: Uint8Array): CreateBoardReq;
  static deserializeBinaryFromReader(
    message: CreateBoardReq,
    reader: jspb.BinaryReader
  ): CreateBoardReq;
}

export namespace CreateBoardReq {
  export type AsObject = {
    title: string;
    description?: google_protobuf_wrappers_pb.StringValue.AsObject;
  };
}

export class CreateBoardRes extends jspb.Message {
  getId(): string;
  setId(value: string): CreateBoardRes;

  serializeBinary(): Uint8Array;
  toObject(includeInstance?: boolean): CreateBoardRes.AsObject;
  static toObject(
    includeInstance: boolean,
    msg: CreateBoardRes
  ): CreateBoardRes.AsObject;
  static serializeBinaryToWriter(
    message: CreateBoardRes,
    writer: jspb.BinaryWriter
  ): void;
  static deserializeBinary(bytes: Uint8Array): CreateBoardRes;
  static deserializeBinaryFromReader(
    message: CreateBoardRes,
    reader: jspb.BinaryReader
  ): CreateBoardRes;
}

export namespace CreateBoardRes {
  export type AsObject = {
    id: string;
  };
}

export class GetBoardReq extends jspb.Message {
  getId(): string;
  setId(value: string): GetBoardReq;

  serializeBinary(): Uint8Array;
  toObject(includeInstance?: boolean): GetBoardReq.AsObject;
  static toObject(
    includeInstance: boolean,
    msg: GetBoardReq
  ): GetBoardReq.AsObject;
  static serializeBinaryToWriter(
    message: GetBoardReq,
    writer: jspb.BinaryWriter
  ): void;
  static deserializeBinary(bytes: Uint8Array): GetBoardReq;
  static deserializeBinaryFromReader(
    message: GetBoardReq,
    reader: jspb.BinaryReader
  ): GetBoardReq;
}

export namespace GetBoardReq {
  export type AsObject = {
    id: string;
  };
}

export class GetBoardRes extends jspb.Message {
  getTitle(): string;
  setTitle(value: string): GetBoardRes;

  getDescription(): google_protobuf_wrappers_pb.StringValue | undefined;
  setDescription(value?: google_protobuf_wrappers_pb.StringValue): GetBoardRes;
  hasDescription(): boolean;
  clearDescription(): GetBoardRes;

  serializeBinary(): Uint8Array;
  toObject(includeInstance?: boolean): GetBoardRes.AsObject;
  static toObject(
    includeInstance: boolean,
    msg: GetBoardRes
  ): GetBoardRes.AsObject;
  static serializeBinaryToWriter(
    message: GetBoardRes,
    writer: jspb.BinaryWriter
  ): void;
  static deserializeBinary(bytes: Uint8Array): GetBoardRes;
  static deserializeBinaryFromReader(
    message: GetBoardRes,
    reader: jspb.BinaryReader
  ): GetBoardRes;
}

export namespace GetBoardRes {
  export type AsObject = {
    title: string;
    description?: google_protobuf_wrappers_pb.StringValue.AsObject;
  };
}

export class ArchiveBoardReq extends jspb.Message {
  getId(): string;
  setId(value: string): ArchiveBoardReq;

  serializeBinary(): Uint8Array;
  toObject(includeInstance?: boolean): ArchiveBoardReq.AsObject;
  static toObject(
    includeInstance: boolean,
    msg: ArchiveBoardReq
  ): ArchiveBoardReq.AsObject;
  static serializeBinaryToWriter(
    message: ArchiveBoardReq,
    writer: jspb.BinaryWriter
  ): void;
  static deserializeBinary(bytes: Uint8Array): ArchiveBoardReq;
  static deserializeBinaryFromReader(
    message: ArchiveBoardReq,
    reader: jspb.BinaryReader
  ): ArchiveBoardReq;
}

export namespace ArchiveBoardReq {
  export type AsObject = {
    id: string;
  };
}

export class ArchiveBoardRes extends jspb.Message {
  serializeBinary(): Uint8Array;
  toObject(includeInstance?: boolean): ArchiveBoardRes.AsObject;
  static toObject(
    includeInstance: boolean,
    msg: ArchiveBoardRes
  ): ArchiveBoardRes.AsObject;
  static serializeBinaryToWriter(
    message: ArchiveBoardRes,
    writer: jspb.BinaryWriter
  ): void;
  static deserializeBinary(bytes: Uint8Array): ArchiveBoardRes;
  static deserializeBinaryFromReader(
    message: ArchiveBoardRes,
    reader: jspb.BinaryReader
  ): ArchiveBoardRes;
}

export namespace ArchiveBoardRes {
  export type AsObject = {};
}

export class UnArchiveBoardReq extends jspb.Message {
  getId(): string;
  setId(value: string): UnArchiveBoardReq;

  serializeBinary(): Uint8Array;
  toObject(includeInstance?: boolean): UnArchiveBoardReq.AsObject;
  static toObject(
    includeInstance: boolean,
    msg: UnArchiveBoardReq
  ): UnArchiveBoardReq.AsObject;
  static serializeBinaryToWriter(
    message: UnArchiveBoardReq,
    writer: jspb.BinaryWriter
  ): void;
  static deserializeBinary(bytes: Uint8Array): UnArchiveBoardReq;
  static deserializeBinaryFromReader(
    message: UnArchiveBoardReq,
    reader: jspb.BinaryReader
  ): UnArchiveBoardReq;
}

export namespace UnArchiveBoardReq {
  export type AsObject = {
    id: string;
  };
}

export class UnArchiveBoardRes extends jspb.Message {
  serializeBinary(): Uint8Array;
  toObject(includeInstance?: boolean): UnArchiveBoardRes.AsObject;
  static toObject(
    includeInstance: boolean,
    msg: UnArchiveBoardRes
  ): UnArchiveBoardRes.AsObject;
  static serializeBinaryToWriter(
    message: UnArchiveBoardRes,
    writer: jspb.BinaryWriter
  ): void;
  static deserializeBinary(bytes: Uint8Array): UnArchiveBoardRes;
  static deserializeBinaryFromReader(
    message: UnArchiveBoardRes,
    reader: jspb.BinaryReader
  ): UnArchiveBoardRes;
}

export namespace UnArchiveBoardRes {
  export type AsObject = {};
}

export class UpdateBoardReq extends jspb.Message {
  getId(): string;
  setId(value: string): UpdateBoardReq;

  getUpdatesList(): Array<UpdateBoardReq.Update>;
  setUpdatesList(value: Array<UpdateBoardReq.Update>): UpdateBoardReq;
  clearUpdatesList(): UpdateBoardReq;
  addUpdates(
    value?: UpdateBoardReq.Update,
    index?: number
  ): UpdateBoardReq.Update;

  serializeBinary(): Uint8Array;
  toObject(includeInstance?: boolean): UpdateBoardReq.AsObject;
  static toObject(
    includeInstance: boolean,
    msg: UpdateBoardReq
  ): UpdateBoardReq.AsObject;
  static serializeBinaryToWriter(
    message: UpdateBoardReq,
    writer: jspb.BinaryWriter
  ): void;
  static deserializeBinary(bytes: Uint8Array): UpdateBoardReq;
  static deserializeBinaryFromReader(
    message: UpdateBoardReq,
    reader: jspb.BinaryReader
  ): UpdateBoardReq;
}

export namespace UpdateBoardReq {
  export type AsObject = {
    id: string;
    updatesList: Array<UpdateBoardReq.Update.AsObject>;
  };

  export class Update extends jspb.Message {
    getPUpdateBoardTitleCmd(): UpdateBoardReq.PUpdateBoardTitleOp | undefined;
    setPUpdateBoardTitleCmd(value?: UpdateBoardReq.PUpdateBoardTitleOp): Update;
    hasPUpdateBoardTitleCmd(): boolean;
    clearPUpdateBoardTitleCmd(): Update;

    getPUpdateBoardDescriptionCmd():
      | UpdateBoardReq.PUpdateBoardDescriptionOp
      | undefined;
    setPUpdateBoardDescriptionCmd(
      value?: UpdateBoardReq.PUpdateBoardDescriptionOp
    ): Update;
    hasPUpdateBoardDescriptionCmd(): boolean;
    clearPUpdateBoardDescriptionCmd(): Update;

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
      pUpdateBoardTitleCmd?: UpdateBoardReq.PUpdateBoardTitleOp.AsObject;
      pUpdateBoardDescriptionCmd?: UpdateBoardReq.PUpdateBoardDescriptionOp.AsObject;
    };

    export enum CmdCase {
      CMD_NOT_SET = 0,
      P_UPDATE_BOARD_TITLE_CMD = 1,
      P_UPDATE_BOARD_DESCRIPTION_CMD = 2,
    }
  }

  export class PUpdateBoardTitleOp extends jspb.Message {
    getTitle(): string;
    setTitle(value: string): PUpdateBoardTitleOp;

    serializeBinary(): Uint8Array;
    toObject(includeInstance?: boolean): PUpdateBoardTitleOp.AsObject;
    static toObject(
      includeInstance: boolean,
      msg: PUpdateBoardTitleOp
    ): PUpdateBoardTitleOp.AsObject;
    static serializeBinaryToWriter(
      message: PUpdateBoardTitleOp,
      writer: jspb.BinaryWriter
    ): void;
    static deserializeBinary(bytes: Uint8Array): PUpdateBoardTitleOp;
    static deserializeBinaryFromReader(
      message: PUpdateBoardTitleOp,
      reader: jspb.BinaryReader
    ): PUpdateBoardTitleOp;
  }

  export namespace PUpdateBoardTitleOp {
    export type AsObject = {
      title: string;
    };
  }

  export class PUpdateBoardDescriptionOp extends jspb.Message {
    getDescription(): google_protobuf_wrappers_pb.StringValue | undefined;
    setDescription(
      value?: google_protobuf_wrappers_pb.StringValue
    ): PUpdateBoardDescriptionOp;
    hasDescription(): boolean;
    clearDescription(): PUpdateBoardDescriptionOp;

    serializeBinary(): Uint8Array;
    toObject(includeInstance?: boolean): PUpdateBoardDescriptionOp.AsObject;
    static toObject(
      includeInstance: boolean,
      msg: PUpdateBoardDescriptionOp
    ): PUpdateBoardDescriptionOp.AsObject;
    static serializeBinaryToWriter(
      message: PUpdateBoardDescriptionOp,
      writer: jspb.BinaryWriter
    ): void;
    static deserializeBinary(bytes: Uint8Array): PUpdateBoardDescriptionOp;
    static deserializeBinaryFromReader(
      message: PUpdateBoardDescriptionOp,
      reader: jspb.BinaryReader
    ): PUpdateBoardDescriptionOp;
  }

  export namespace PUpdateBoardDescriptionOp {
    export type AsObject = {
      description?: google_protobuf_wrappers_pb.StringValue.AsObject;
    };
  }
}

export class UpdateBoardRes extends jspb.Message {
  serializeBinary(): Uint8Array;
  toObject(includeInstance?: boolean): UpdateBoardRes.AsObject;
  static toObject(
    includeInstance: boolean,
    msg: UpdateBoardRes
  ): UpdateBoardRes.AsObject;
  static serializeBinaryToWriter(
    message: UpdateBoardRes,
    writer: jspb.BinaryWriter
  ): void;
  static deserializeBinary(bytes: Uint8Array): UpdateBoardRes;
  static deserializeBinaryFromReader(
    message: UpdateBoardRes,
    reader: jspb.BinaryReader
  ): UpdateBoardRes;
}

export namespace UpdateBoardRes {
  export type AsObject = {};
}
