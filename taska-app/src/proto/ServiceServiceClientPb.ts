/**
 * @fileoverview gRPC-Web generated client stub for taska.proto
 * @enhanceable
 * @public
 */

// GENERATED CODE -- DO NOT EDIT!

/* eslint-disable */
// @ts-nocheck

import * as grpcWeb from "grpc-web";

import * as board_pb from "./board_pb";
import * as card_pb from "./card_pb";
import * as list_pb from "./list_pb";

export class TaskaServiceClient {
  client_: grpcWeb.AbstractClientBase;
  hostname_: string;
  credentials_: null | { [index: string]: string };
  options_: null | { [index: string]: any };

  constructor(
    hostname: string,
    credentials?: null | { [index: string]: string },
    options?: null | { [index: string]: any }
  ) {
    if (!options) options = {};
    if (!credentials) credentials = {};
    options["format"] = "binary";

    this.client_ = new grpcWeb.GrpcWebClientBase(options);
    this.hostname_ = hostname;
    this.credentials_ = credentials;
    this.options_ = options;
  }

  methodInfoCreateBoard = new grpcWeb.AbstractClientBase.MethodInfo(
    board_pb.CreateBoardRes,
    (request: board_pb.CreateBoardReq) => {
      return request.serializeBinary();
    },
    board_pb.CreateBoardRes.deserializeBinary
  );

  createBoard(
    request: board_pb.CreateBoardReq,
    metadata: grpcWeb.Metadata | null
  ): Promise<board_pb.CreateBoardRes>;

  createBoard(
    request: board_pb.CreateBoardReq,
    metadata: grpcWeb.Metadata | null,
    callback: (err: grpcWeb.Error, response: board_pb.CreateBoardRes) => void
  ): grpcWeb.ClientReadableStream<board_pb.CreateBoardRes>;

  createBoard(
    request: board_pb.CreateBoardReq,
    metadata: grpcWeb.Metadata | null,
    callback?: (err: grpcWeb.Error, response: board_pb.CreateBoardRes) => void
  ) {
    if (callback !== undefined) {
      return this.client_.rpcCall(
        this.hostname_ + "/taska.proto.TaskaService/CreateBoard",
        request,
        metadata || {},
        this.methodInfoCreateBoard,
        callback
      );
    }
    return this.client_.unaryCall(
      this.hostname_ + "/taska.proto.TaskaService/CreateBoard",
      request,
      metadata || {},
      this.methodInfoCreateBoard
    );
  }

  methodInfoGetBoard = new grpcWeb.AbstractClientBase.MethodInfo(
    board_pb.GetBoardRes,
    (request: board_pb.GetBoardReq) => {
      return request.serializeBinary();
    },
    board_pb.GetBoardRes.deserializeBinary
  );

  getBoard(
    request: board_pb.GetBoardReq,
    metadata: grpcWeb.Metadata | null
  ): Promise<board_pb.GetBoardRes>;

  getBoard(
    request: board_pb.GetBoardReq,
    metadata: grpcWeb.Metadata | null,
    callback: (err: grpcWeb.Error, response: board_pb.GetBoardRes) => void
  ): grpcWeb.ClientReadableStream<board_pb.GetBoardRes>;

  getBoard(
    request: board_pb.GetBoardReq,
    metadata: grpcWeb.Metadata | null,
    callback?: (err: grpcWeb.Error, response: board_pb.GetBoardRes) => void
  ) {
    if (callback !== undefined) {
      return this.client_.rpcCall(
        this.hostname_ + "/taska.proto.TaskaService/GetBoard",
        request,
        metadata || {},
        this.methodInfoGetBoard,
        callback
      );
    }
    return this.client_.unaryCall(
      this.hostname_ + "/taska.proto.TaskaService/GetBoard",
      request,
      metadata || {},
      this.methodInfoGetBoard
    );
  }

  methodInfoArchiveBoard = new grpcWeb.AbstractClientBase.MethodInfo(
    board_pb.ArchiveBoardRes,
    (request: board_pb.ArchiveBoardReq) => {
      return request.serializeBinary();
    },
    board_pb.ArchiveBoardRes.deserializeBinary
  );

  archiveBoard(
    request: board_pb.ArchiveBoardReq,
    metadata: grpcWeb.Metadata | null
  ): Promise<board_pb.ArchiveBoardRes>;

  archiveBoard(
    request: board_pb.ArchiveBoardReq,
    metadata: grpcWeb.Metadata | null,
    callback: (err: grpcWeb.Error, response: board_pb.ArchiveBoardRes) => void
  ): grpcWeb.ClientReadableStream<board_pb.ArchiveBoardRes>;

  archiveBoard(
    request: board_pb.ArchiveBoardReq,
    metadata: grpcWeb.Metadata | null,
    callback?: (err: grpcWeb.Error, response: board_pb.ArchiveBoardRes) => void
  ) {
    if (callback !== undefined) {
      return this.client_.rpcCall(
        this.hostname_ + "/taska.proto.TaskaService/ArchiveBoard",
        request,
        metadata || {},
        this.methodInfoArchiveBoard,
        callback
      );
    }
    return this.client_.unaryCall(
      this.hostname_ + "/taska.proto.TaskaService/ArchiveBoard",
      request,
      metadata || {},
      this.methodInfoArchiveBoard
    );
  }

  methodInfoUnArchiveBoard = new grpcWeb.AbstractClientBase.MethodInfo(
    board_pb.UnArchiveBoardRes,
    (request: board_pb.UnArchiveBoardReq) => {
      return request.serializeBinary();
    },
    board_pb.UnArchiveBoardRes.deserializeBinary
  );

  unArchiveBoard(
    request: board_pb.UnArchiveBoardReq,
    metadata: grpcWeb.Metadata | null
  ): Promise<board_pb.UnArchiveBoardRes>;

  unArchiveBoard(
    request: board_pb.UnArchiveBoardReq,
    metadata: grpcWeb.Metadata | null,
    callback: (err: grpcWeb.Error, response: board_pb.UnArchiveBoardRes) => void
  ): grpcWeb.ClientReadableStream<board_pb.UnArchiveBoardRes>;

  unArchiveBoard(
    request: board_pb.UnArchiveBoardReq,
    metadata: grpcWeb.Metadata | null,
    callback?: (
      err: grpcWeb.Error,
      response: board_pb.UnArchiveBoardRes
    ) => void
  ) {
    if (callback !== undefined) {
      return this.client_.rpcCall(
        this.hostname_ + "/taska.proto.TaskaService/UnArchiveBoard",
        request,
        metadata || {},
        this.methodInfoUnArchiveBoard,
        callback
      );
    }
    return this.client_.unaryCall(
      this.hostname_ + "/taska.proto.TaskaService/UnArchiveBoard",
      request,
      metadata || {},
      this.methodInfoUnArchiveBoard
    );
  }

  methodInfoUpdateBoard = new grpcWeb.AbstractClientBase.MethodInfo(
    board_pb.UpdateBoardRes,
    (request: board_pb.UpdateBoardReq) => {
      return request.serializeBinary();
    },
    board_pb.UpdateBoardRes.deserializeBinary
  );

  updateBoard(
    request: board_pb.UpdateBoardReq,
    metadata: grpcWeb.Metadata | null
  ): Promise<board_pb.UpdateBoardRes>;

  updateBoard(
    request: board_pb.UpdateBoardReq,
    metadata: grpcWeb.Metadata | null,
    callback: (err: grpcWeb.Error, response: board_pb.UpdateBoardRes) => void
  ): grpcWeb.ClientReadableStream<board_pb.UpdateBoardRes>;

  updateBoard(
    request: board_pb.UpdateBoardReq,
    metadata: grpcWeb.Metadata | null,
    callback?: (err: grpcWeb.Error, response: board_pb.UpdateBoardRes) => void
  ) {
    if (callback !== undefined) {
      return this.client_.rpcCall(
        this.hostname_ + "/taska.proto.TaskaService/UpdateBoard",
        request,
        metadata || {},
        this.methodInfoUpdateBoard,
        callback
      );
    }
    return this.client_.unaryCall(
      this.hostname_ + "/taska.proto.TaskaService/UpdateBoard",
      request,
      metadata || {},
      this.methodInfoUpdateBoard
    );
  }

  methodInfoCreateList = new grpcWeb.AbstractClientBase.MethodInfo(
    list_pb.CreateListRes,
    (request: list_pb.CreateListReq) => {
      return request.serializeBinary();
    },
    list_pb.CreateListRes.deserializeBinary
  );

  createList(
    request: list_pb.CreateListReq,
    metadata: grpcWeb.Metadata | null
  ): Promise<list_pb.CreateListRes>;

  createList(
    request: list_pb.CreateListReq,
    metadata: grpcWeb.Metadata | null,
    callback: (err: grpcWeb.Error, response: list_pb.CreateListRes) => void
  ): grpcWeb.ClientReadableStream<list_pb.CreateListRes>;

  createList(
    request: list_pb.CreateListReq,
    metadata: grpcWeb.Metadata | null,
    callback?: (err: grpcWeb.Error, response: list_pb.CreateListRes) => void
  ) {
    if (callback !== undefined) {
      return this.client_.rpcCall(
        this.hostname_ + "/taska.proto.TaskaService/CreateList",
        request,
        metadata || {},
        this.methodInfoCreateList,
        callback
      );
    }
    return this.client_.unaryCall(
      this.hostname_ + "/taska.proto.TaskaService/CreateList",
      request,
      metadata || {},
      this.methodInfoCreateList
    );
  }

  methodInfoGetList = new grpcWeb.AbstractClientBase.MethodInfo(
    list_pb.GetListRes,
    (request: list_pb.GetListReq) => {
      return request.serializeBinary();
    },
    list_pb.GetListRes.deserializeBinary
  );

  getList(
    request: list_pb.GetListReq,
    metadata: grpcWeb.Metadata | null
  ): Promise<list_pb.GetListRes>;

  getList(
    request: list_pb.GetListReq,
    metadata: grpcWeb.Metadata | null,
    callback: (err: grpcWeb.Error, response: list_pb.GetListRes) => void
  ): grpcWeb.ClientReadableStream<list_pb.GetListRes>;

  getList(
    request: list_pb.GetListReq,
    metadata: grpcWeb.Metadata | null,
    callback?: (err: grpcWeb.Error, response: list_pb.GetListRes) => void
  ) {
    if (callback !== undefined) {
      return this.client_.rpcCall(
        this.hostname_ + "/taska.proto.TaskaService/GetList",
        request,
        metadata || {},
        this.methodInfoGetList,
        callback
      );
    }
    return this.client_.unaryCall(
      this.hostname_ + "/taska.proto.TaskaService/GetList",
      request,
      metadata || {},
      this.methodInfoGetList
    );
  }

  methodInfoArchiveList = new grpcWeb.AbstractClientBase.MethodInfo(
    list_pb.ArchiveListRes,
    (request: list_pb.ArchiveListReq) => {
      return request.serializeBinary();
    },
    list_pb.ArchiveListRes.deserializeBinary
  );

  archiveList(
    request: list_pb.ArchiveListReq,
    metadata: grpcWeb.Metadata | null
  ): Promise<list_pb.ArchiveListRes>;

  archiveList(
    request: list_pb.ArchiveListReq,
    metadata: grpcWeb.Metadata | null,
    callback: (err: grpcWeb.Error, response: list_pb.ArchiveListRes) => void
  ): grpcWeb.ClientReadableStream<list_pb.ArchiveListRes>;

  archiveList(
    request: list_pb.ArchiveListReq,
    metadata: grpcWeb.Metadata | null,
    callback?: (err: grpcWeb.Error, response: list_pb.ArchiveListRes) => void
  ) {
    if (callback !== undefined) {
      return this.client_.rpcCall(
        this.hostname_ + "/taska.proto.TaskaService/ArchiveList",
        request,
        metadata || {},
        this.methodInfoArchiveList,
        callback
      );
    }
    return this.client_.unaryCall(
      this.hostname_ + "/taska.proto.TaskaService/ArchiveList",
      request,
      metadata || {},
      this.methodInfoArchiveList
    );
  }

  methodInfoUnArchiveList = new grpcWeb.AbstractClientBase.MethodInfo(
    list_pb.UnArchiveListRes,
    (request: list_pb.UnArchiveListReq) => {
      return request.serializeBinary();
    },
    list_pb.UnArchiveListRes.deserializeBinary
  );

  unArchiveList(
    request: list_pb.UnArchiveListReq,
    metadata: grpcWeb.Metadata | null
  ): Promise<list_pb.UnArchiveListRes>;

  unArchiveList(
    request: list_pb.UnArchiveListReq,
    metadata: grpcWeb.Metadata | null,
    callback: (err: grpcWeb.Error, response: list_pb.UnArchiveListRes) => void
  ): grpcWeb.ClientReadableStream<list_pb.UnArchiveListRes>;

  unArchiveList(
    request: list_pb.UnArchiveListReq,
    metadata: grpcWeb.Metadata | null,
    callback?: (err: grpcWeb.Error, response: list_pb.UnArchiveListRes) => void
  ) {
    if (callback !== undefined) {
      return this.client_.rpcCall(
        this.hostname_ + "/taska.proto.TaskaService/UnArchiveList",
        request,
        metadata || {},
        this.methodInfoUnArchiveList,
        callback
      );
    }
    return this.client_.unaryCall(
      this.hostname_ + "/taska.proto.TaskaService/UnArchiveList",
      request,
      metadata || {},
      this.methodInfoUnArchiveList
    );
  }

  methodInfoUpdateList = new grpcWeb.AbstractClientBase.MethodInfo(
    list_pb.UpdateListRes,
    (request: list_pb.UpdateListReq) => {
      return request.serializeBinary();
    },
    list_pb.UpdateListRes.deserializeBinary
  );

  updateList(
    request: list_pb.UpdateListReq,
    metadata: grpcWeb.Metadata | null
  ): Promise<list_pb.UpdateListRes>;

  updateList(
    request: list_pb.UpdateListReq,
    metadata: grpcWeb.Metadata | null,
    callback: (err: grpcWeb.Error, response: list_pb.UpdateListRes) => void
  ): grpcWeb.ClientReadableStream<list_pb.UpdateListRes>;

  updateList(
    request: list_pb.UpdateListReq,
    metadata: grpcWeb.Metadata | null,
    callback?: (err: grpcWeb.Error, response: list_pb.UpdateListRes) => void
  ) {
    if (callback !== undefined) {
      return this.client_.rpcCall(
        this.hostname_ + "/taska.proto.TaskaService/UpdateList",
        request,
        metadata || {},
        this.methodInfoUpdateList,
        callback
      );
    }
    return this.client_.unaryCall(
      this.hostname_ + "/taska.proto.TaskaService/UpdateList",
      request,
      metadata || {},
      this.methodInfoUpdateList
    );
  }

  methodInfoCreateCard = new grpcWeb.AbstractClientBase.MethodInfo(
    card_pb.CreateCardRes,
    (request: card_pb.CreateCardReq) => {
      return request.serializeBinary();
    },
    card_pb.CreateCardRes.deserializeBinary
  );

  createCard(
    request: card_pb.CreateCardReq,
    metadata: grpcWeb.Metadata | null
  ): Promise<card_pb.CreateCardRes>;

  createCard(
    request: card_pb.CreateCardReq,
    metadata: grpcWeb.Metadata | null,
    callback: (err: grpcWeb.Error, response: card_pb.CreateCardRes) => void
  ): grpcWeb.ClientReadableStream<card_pb.CreateCardRes>;

  createCard(
    request: card_pb.CreateCardReq,
    metadata: grpcWeb.Metadata | null,
    callback?: (err: grpcWeb.Error, response: card_pb.CreateCardRes) => void
  ) {
    if (callback !== undefined) {
      return this.client_.rpcCall(
        this.hostname_ + "/taska.proto.TaskaService/CreateCard",
        request,
        metadata || {},
        this.methodInfoCreateCard,
        callback
      );
    }
    return this.client_.unaryCall(
      this.hostname_ + "/taska.proto.TaskaService/CreateCard",
      request,
      metadata || {},
      this.methodInfoCreateCard
    );
  }

  methodInfoGetCard = new grpcWeb.AbstractClientBase.MethodInfo(
    card_pb.GetCardRes,
    (request: card_pb.GetCardReq) => {
      return request.serializeBinary();
    },
    card_pb.GetCardRes.deserializeBinary
  );

  getCard(
    request: card_pb.GetCardReq,
    metadata: grpcWeb.Metadata | null
  ): Promise<card_pb.GetCardRes>;

  getCard(
    request: card_pb.GetCardReq,
    metadata: grpcWeb.Metadata | null,
    callback: (err: grpcWeb.Error, response: card_pb.GetCardRes) => void
  ): grpcWeb.ClientReadableStream<card_pb.GetCardRes>;

  getCard(
    request: card_pb.GetCardReq,
    metadata: grpcWeb.Metadata | null,
    callback?: (err: grpcWeb.Error, response: card_pb.GetCardRes) => void
  ) {
    if (callback !== undefined) {
      return this.client_.rpcCall(
        this.hostname_ + "/taska.proto.TaskaService/GetCard",
        request,
        metadata || {},
        this.methodInfoGetCard,
        callback
      );
    }
    return this.client_.unaryCall(
      this.hostname_ + "/taska.proto.TaskaService/GetCard",
      request,
      metadata || {},
      this.methodInfoGetCard
    );
  }

  methodInfoArchiveCard = new grpcWeb.AbstractClientBase.MethodInfo(
    card_pb.ArchiveCardRes,
    (request: card_pb.ArchiveCardReq) => {
      return request.serializeBinary();
    },
    card_pb.ArchiveCardRes.deserializeBinary
  );

  archiveCard(
    request: card_pb.ArchiveCardReq,
    metadata: grpcWeb.Metadata | null
  ): Promise<card_pb.ArchiveCardRes>;

  archiveCard(
    request: card_pb.ArchiveCardReq,
    metadata: grpcWeb.Metadata | null,
    callback: (err: grpcWeb.Error, response: card_pb.ArchiveCardRes) => void
  ): grpcWeb.ClientReadableStream<card_pb.ArchiveCardRes>;

  archiveCard(
    request: card_pb.ArchiveCardReq,
    metadata: grpcWeb.Metadata | null,
    callback?: (err: grpcWeb.Error, response: card_pb.ArchiveCardRes) => void
  ) {
    if (callback !== undefined) {
      return this.client_.rpcCall(
        this.hostname_ + "/taska.proto.TaskaService/ArchiveCard",
        request,
        metadata || {},
        this.methodInfoArchiveCard,
        callback
      );
    }
    return this.client_.unaryCall(
      this.hostname_ + "/taska.proto.TaskaService/ArchiveCard",
      request,
      metadata || {},
      this.methodInfoArchiveCard
    );
  }

  methodInfoUnArchiveCard = new grpcWeb.AbstractClientBase.MethodInfo(
    card_pb.UnArchiveCardRes,
    (request: card_pb.UnArchiveCardReq) => {
      return request.serializeBinary();
    },
    card_pb.UnArchiveCardRes.deserializeBinary
  );

  unArchiveCard(
    request: card_pb.UnArchiveCardReq,
    metadata: grpcWeb.Metadata | null
  ): Promise<card_pb.UnArchiveCardRes>;

  unArchiveCard(
    request: card_pb.UnArchiveCardReq,
    metadata: grpcWeb.Metadata | null,
    callback: (err: grpcWeb.Error, response: card_pb.UnArchiveCardRes) => void
  ): grpcWeb.ClientReadableStream<card_pb.UnArchiveCardRes>;

  unArchiveCard(
    request: card_pb.UnArchiveCardReq,
    metadata: grpcWeb.Metadata | null,
    callback?: (err: grpcWeb.Error, response: card_pb.UnArchiveCardRes) => void
  ) {
    if (callback !== undefined) {
      return this.client_.rpcCall(
        this.hostname_ + "/taska.proto.TaskaService/UnArchiveCard",
        request,
        metadata || {},
        this.methodInfoUnArchiveCard,
        callback
      );
    }
    return this.client_.unaryCall(
      this.hostname_ + "/taska.proto.TaskaService/UnArchiveCard",
      request,
      metadata || {},
      this.methodInfoUnArchiveCard
    );
  }

  methodInfoUpdateCard = new grpcWeb.AbstractClientBase.MethodInfo(
    card_pb.UpdateCardRes,
    (request: card_pb.UpdateCardReq) => {
      return request.serializeBinary();
    },
    card_pb.UpdateCardRes.deserializeBinary
  );

  updateCard(
    request: card_pb.UpdateCardReq,
    metadata: grpcWeb.Metadata | null
  ): Promise<card_pb.UpdateCardRes>;

  updateCard(
    request: card_pb.UpdateCardReq,
    metadata: grpcWeb.Metadata | null,
    callback: (err: grpcWeb.Error, response: card_pb.UpdateCardRes) => void
  ): grpcWeb.ClientReadableStream<card_pb.UpdateCardRes>;

  updateCard(
    request: card_pb.UpdateCardReq,
    metadata: grpcWeb.Metadata | null,
    callback?: (err: grpcWeb.Error, response: card_pb.UpdateCardRes) => void
  ) {
    if (callback !== undefined) {
      return this.client_.rpcCall(
        this.hostname_ + "/taska.proto.TaskaService/UpdateCard",
        request,
        metadata || {},
        this.methodInfoUpdateCard,
        callback
      );
    }
    return this.client_.unaryCall(
      this.hostname_ + "/taska.proto.TaskaService/UpdateCard",
      request,
      metadata || {},
      this.methodInfoUpdateCard
    );
  }
}
