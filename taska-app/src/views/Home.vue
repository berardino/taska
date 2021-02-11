<template>
  <div class="home">
    ciao ciao {{ boardId }}
  </div>
</template>

<script lang="ts">
import { Component, Vue } from "vue-property-decorator";
import HelloWorld from "@/components/HelloWorld.vue"; // @ is an alias to /src
import { TaskaServiceClient } from "@/proto/ServiceServiceClientPb";
import { CreateBoardReq } from "@/proto/board_pb";
import { StringValue } from "google-protobuf/google/protobuf/wrappers_pb";

@Component({
  components: {
    HelloWorld
  }
})
export default class Home extends Vue {
  private taskaClient: TaskaServiceClient = new TaskaServiceClient(
    "http://localhost:8080",
    null,
    null
  );

  private boardId = "";

  created(): void {
    const req = new CreateBoardReq();
    req.setTitle("title");
    const desc = new StringValue();
    desc.setValue("description");
    req.setDescription(desc);
    this.taskaClient.createBoard(req, null).then(res => {
      this.boardId = res.getId();
    });
  }
}
</script>
