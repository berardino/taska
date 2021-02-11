<template>
  <div id="app">
    <header>
      <div>
        <b-navbar toggleable="lg" variant="light">
          <b-navbar-brand href="#">
            <font-awesome-icon icon="home"
          /></b-navbar-brand>

          <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>

          <b-collapse id="nav-collapse" is-nav>
            <b-navbar-nav>
              <b-nav-item href="#">Boards</b-nav-item>
              <b-nav-form>
                <b-form-input
                  size="sm"
                  class="mr-sm-2"
                  placeholder="Search"
                ></b-form-input>
              </b-nav-form>
            </b-navbar-nav>

            <!-- Right aligned nav items -->
            <b-navbar-nav class="ml-auto">
              <b-nav-item-dropdown right>
                <template #button-content>
                  <em><font-awesome-icon icon="plus"/></em>
                </template>
                <b-dropdown-item href="#">Create board</b-dropdown-item>
              </b-nav-item-dropdown>
              <b-nav-item-dropdown right>
                <template #button-content>
                  <em><font-awesome-icon icon="user"/></em>
                </template>
                <b-dropdown-item href="#">Sign Out</b-dropdown-item>
              </b-nav-item-dropdown>
            </b-navbar-nav>
          </b-collapse>
        </b-navbar>
      </div>
    </header>

    <main role="main">
        <router-view />
     </main>
  </div>
</template>
