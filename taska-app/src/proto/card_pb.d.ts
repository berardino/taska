import * as jspb from "google-protobuf";

import * as google_protobuf_wrappers_pb from "google-protobuf/google/protobuf/wrappers_pb";

export class CreateCardReq extends jspb.Message {
  getListId(): string;
  setListId(value: string): CreateCardReq;

  getTitle(): string;
  setTitle(value: string): CreateCardReq;

  getDescription(): google_protobuf_wrappers_pb.StringValue | undefined;
  setDescription(
    value?: google_protobuf_wrappers_pb.StringValue
  ): CreateCardReq;
  hasDescription(): boolean;
  clearDescription(): CreateCardReq;

  serializeBinary(): Uint8Array;
  toObject(includeInstance?: boolean): CreateCardReq.AsObject;
  static toObject(
    includeInstance: boolean,
    msg: CreateCardReq
  ): CreateCardReq.AsObject;
  static serializeBinaryToWriter(
    message: CreateCardReq,
    writer: jspb.BinaryWriter
  ): void;
  static deserializeBinary(bytes: Uint8Array): CreateCardReq;
  static deserializeBinaryFromReader(
    message: CreateCardReq,
    reader: jspb.BinaryReader
  ): CreateCardReq;
}

export namespace CreateCardReq {
  export type AsObject = {
    listId: string;
    title: string;
    description?: google_protobuf_wrappers_pb.StringValue.AsObject;
  };
}

export class CreateCardRes extends jspb.Message {
  getId(): string;
  setId(value: string): CreateCardRes;

  serializeBinary(): Uint8Array;
  toObject(includeInstance?: boolean): CreateCardRes.AsObject;
  static toObject(
    includeInstance: boolean,
    msg: CreateCardRes
  ): CreateCardRes.AsObject;
  static serializeBinaryToWriter(
    message: CreateCardRes,
    writer: jspb.BinaryWriter
  ): void;
  static deserializeBinary(bytes: Uint8Array): CreateCardRes;
  static deserializeBinaryFromReader(
    message: CreateCardRes,
    reader: jspb.BinaryReader
  ): CreateCardRes;
}

export namespace CreateCardRes {
  export type AsObject = {
    id: string;
  };
}

export class GetCardReq extends jspb.Message {
  getId(): string;
  setId(value: string): GetCardReq;

  serializeBinary(): Uint8Array;
  toObject(includeInstance?: boolean): GetCardReq.AsObject;
  static toObject(
    includeInstance: boolean,
    msg: GetCardReq
  ): GetCardReq.AsObject;
  static serializeBinaryToWriter(
    message: GetCardReq,
    writer: jspb.BinaryWriter
  ): void;
  static deserializeBinary(bytes: Uint8Array): GetCardReq;
  static deserializeBinaryFromReader(
    message: GetCardReq,
    reader: jspb.BinaryReader
  ): GetCardReq;
}

export namespace GetCardReq {
  export type AsObject = {
    id: string;
  };
}

export class GetCardRes extends jspb.Message {
  getListId(): string;
  setListId(value: string): GetCardRes;

  getTitle(): string;
  setTitle(value: string): GetCardRes;

  getDescription(): google_protobuf_wrappers_pb.StringValue | undefined;
  setDescription(value?: google_protobuf_wrappers_pb.StringValue): GetCardRes;
  hasDescription(): boolean;
  clearDescription(): GetCardRes;

  serializeBinary(): Uint8Array;
  toObject(includeInstance?: boolean): GetCardRes.AsObject;
  static toObject(
    includeInstance: boolean,
    msg: GetCardRes
  ): GetCardRes.AsObject;
  static serializeBinaryToWriter(
    message: GetCardRes,
    writer: jspb.BinaryWriter
  ): void;
  static deserializeBinary(bytes: Uint8Array): GetCardRes;
  static deserializeBinaryFromReader(
    message: GetCardRes,
    reader: jspb.BinaryReader
  ): GetCardRes;
}

export namespace GetCardRes {
  export type AsObject = {
    listId: string;
    title: string;
    description?: google_protobuf_wrappers_pb.StringValue.AsObject;
  };
}

export class ArchiveCardReq extends jspb.Message {
  getId(): string;
  setId(value: string): ArchiveCardReq;

  serializeBinary(): Uint8Array;
  toObject(includeInstance?: boolean): ArchiveCardReq.AsObject;
  static toObject(
    includeInstance: boolean,
    msg: ArchiveCardReq
  ): ArchiveCardReq.AsObject;
  static serializeBinaryToWriter(
    message: ArchiveCardReq,
    writer: jspb.BinaryWriter
  ): void;
  static deserializeBinary(bytes: Uint8Array): ArchiveCardReq;
  static deserializeBinaryFromReader(
    message: ArchiveCardReq,
    reader: jspb.BinaryReader
  ): ArchiveCardReq;
}

export namespace ArchiveCardReq {
  export type AsObject = {
    id: string;
  };
}

export class ArchiveCardRes extends jspb.Message {
  serializeBinary(): Uint8Array;
  toObject(includeInstance?: boolean): ArchiveCardRes.AsObject;
  static toObject(
    includeInstance: boolean,
    msg: ArchiveCardRes
  ): ArchiveCardRes.AsObject;
  static serializeBinaryToWriter(
    message: ArchiveCardRes,
    writer: jspb.BinaryWriter
  ): void;
  static deserializeBinary(bytes: Uint8Array): ArchiveCardRes;
  static deserializeBinaryFromReader(
    message: ArchiveCardRes,
    reader: jspb.BinaryReader
  ): ArchiveCardRes;
}

export namespace ArchiveCardRes {
  export type AsObject = {};
}

export class UnArchiveCardReq extends jspb.Message {
  getId(): string;
  setId(value: string): UnArchiveCardReq;

  serializeBinary(): Uint8Array;
  toObject(includeInstance?: boolean): UnArchiveCardReq.AsObject;
  static toObject(
    includeInstance: boolean,
    msg: UnArchiveCardReq
  ): UnArchiveCardReq.AsObject;
  static serializeBinaryToWriter(
    message: UnArchiveCardReq,
    writer: jspb.BinaryWriter
  ): void;
  static deserializeBinary(bytes: Uint8Array): UnArchiveCardReq;
  static deserializeBinaryFromReader(
    message: UnArchiveCardReq,
    reader: jspb.BinaryReader
  ): UnArchiveCardReq;
}

export namespace UnArchiveCardReq {
  export type AsObject = {
    id: string;
  };
}

export class UnArchiveCardRes extends jspb.Message {
  serializeBinary(): Uint8Array;
  toObject(includeInstance?: boolean): UnArchiveCardRes.AsObject;
  static toObject(
    includeInstance: boolean,
    msg: UnArchiveCardRes
  ): UnArchiveCardRes.AsObject;
  static serializeBinaryToWriter(
    message: UnArchiveCardRes,
    writer: jspb.BinaryWriter
  ): void;
  static deserializeBinary(bytes: Uint8Array): UnArchiveCardRes;
  static deserializeBinaryFromReader(
    message: UnArchiveCardRes,
    reader: jspb.BinaryReader
  ): UnArchiveCardRes;
}

export namespace UnArchiveCardRes {
  export type AsObject = {};
}

export class UpdateCardReq extends jspb.Message {
  getId(): string;
  setId(value: string): UpdateCardReq;

  getUpdatesList(): Array<UpdateCardReq.Update>;
  setUpdatesList(value: Array<UpdateCardReq.Update>): UpdateCardReq;
  clearUpdatesList(): UpdateCardReq;
  addUpdates(
    value?: UpdateCardReq.Update,
    index?: number
  ): UpdateCardReq.Update;

  serializeBinary(): Uint8Array;
  toObject(includeInstance?: boolean): UpdateCardReq.AsObject;
  static toObject(
    includeInstance: boolean,
    msg: UpdateCardReq
  ): UpdateCardReq.AsObject;
  static serializeBinaryToWriter(
    message: UpdateCardReq,
    writer: jspb.BinaryWriter
  ): void;
  static deserializeBinary(bytes: Uint8Array): UpdateCardReq;
  static deserializeBinaryFromReader(
    message: UpdateCardReq,
    reader: jspb.BinaryReader
  ): UpdateCardReq;
}

export namespace UpdateCardReq {
  export type AsObject = {
    id: string;
    updatesList: Array<UpdateCardReq.Update.AsObject>;
  };

  export class Update extends jspb.Message {
    getPUpdateCardTitleCmd(): UpdateCardReq.PUpdateCardTitleOp | undefined;
    setPUpdateCardTitleCmd(value?: UpdateCardReq.PUpdateCardTitleOp): Update;
    hasPUpdateCardTitleCmd(): boolean;
    clearPUpdateCardTitleCmd(): Update;

    getPUpdateCardDescriptionCmd():
      | UpdateCardReq.PUpdateCardDescriptionOp
      | undefined;
    setPUpdateCardDescriptionCmd(
      value?: UpdateCardReq.PUpdateCardDescriptionOp
    ): Update;
    hasPUpdateCardDescriptionCmd(): boolean;
    clearPUpdateCardDescriptionCmd(): Update;

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
      pUpdateCardTitleCmd?: UpdateCardReq.PUpdateCardTitleOp.AsObject;
      pUpdateCardDescriptionCmd?: UpdateCardReq.PUpdateCardDescriptionOp.AsObject;
    };

    export enum CmdCase {
      CMD_NOT_SET = 0,
      P_UPDATE_CARD_TITLE_CMD = 1,
      P_UPDATE_CARD_DESCRIPTION_CMD = 2,
    }
  }

  export class PUpdateCardTitleOp extends jspb.Message {
    getTitle(): string;
    setTitle(value: string): PUpdateCardTitleOp;

    serializeBinary(): Uint8Array;
    toObject(includeInstance?: boolean): PUpdateCardTitleOp.AsObject;
    static toObject(
      includeInstance: boolean,
      msg: PUpdateCardTitleOp
    ): PUpdateCardTitleOp.AsObject;
    static serializeBinaryToWriter(
      message: PUpdateCardTitleOp,
      writer: jspb.BinaryWriter
    ): void;
    static deserializeBinary(bytes: Uint8Array): PUpdateCardTitleOp;
    static deserializeBinaryFromReader(
      message: PUpdateCardTitleOp,
      reader: jspb.BinaryReader
    ): PUpdateCardTitleOp;
  }

  export namespace PUpdateCardTitleOp {
    export type AsObject = {
      title: string;
    };
  }

  export class PUpdateCardDescriptionOp extends jspb.Message {
    getDescription(): google_protobuf_wrappers_pb.StringValue | undefined;
    setDescription(
      value?: google_protobuf_wrappers_pb.StringValue
    ): PUpdateCardDescriptionOp;
    hasDescription(): boolean;
    clearDescription(): PUpdateCardDescriptionOp;

    serializeBinary(): Uint8Array;
    toObject(includeInstance?: boolean): PUpdateCardDescriptionOp.AsObject;
    static toObject(
      includeInstance: boolean,
      msg: PUpdateCardDescriptionOp
    ): PUpdateCardDescriptionOp.AsObject;
    static serializeBinaryToWriter(
      message: PUpdateCardDescriptionOp,
      writer: jspb.BinaryWriter
    ): void;
    static deserializeBinary(bytes: Uint8Array): PUpdateCardDescriptionOp;
    static deserializeBinaryFromReader(
      message: PUpdateCardDescriptionOp,
      reader: jspb.BinaryReader
    ): PUpdateCardDescriptionOp;
  }

  export namespace PUpdateCardDescriptionOp {
    export type AsObject = {
      description?: google_protobuf_wrappers_pb.StringValue.AsObject;
    };
  }
}

export class UpdateCardRes extends jspb.Message {
  serializeBinary(): Uint8Array;
  toObject(includeInstance?: boolean): UpdateCardRes.AsObject;
  static toObject(
    includeInstance: boolean,
    msg: UpdateCardRes
  ): UpdateCardRes.AsObject;
  static serializeBinaryToWriter(
    message: UpdateCardRes,
    writer: jspb.BinaryWriter
  ): void;
  static deserializeBinary(bytes: Uint8Array): UpdateCardRes;
  static deserializeBinaryFromReader(
    message: UpdateCardRes,
    reader: jspb.BinaryReader
  ): UpdateCardRes;
}

export namespace UpdateCardRes {
  export type AsObject = {};
}
