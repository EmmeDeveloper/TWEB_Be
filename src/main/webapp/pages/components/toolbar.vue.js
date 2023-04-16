Vue.component("spa-toolbar", {
  template: `<div>
    <v-navigation-drawer dark app clipped temporary v-model="menu" class="blue white--text">
        <v-list >
            <v-list-item
               v-for="item in items" :key="item.title" @click="$router.push(item.path)">
                <v-list-item-action>
                    <v-icon>{{ item.icon }}</v-icon>
                </v-list-item-action>

                <v-list-item-content>
                    <v-list-item-title>{{ item.title }}</v-list-item-title>
                </v-list-item-content>
            </v-list-item>
        </v-list>
    </v-navigation-drawer>
    <v-app-bar dark color="primary" class="mb-2" fixed app> 
        <v-layout align-center>
            <v-app-bar-nav-icon v-on:click="menu=!menu"></v-app-bar-nav-icon>
            <v-toolbar class="white--text">{{title}} {{email}}</v-toolbar>
            <v-spacer></v-spacer>
            <v-toolbar-items>
                <v-btn v-if="email != '??'" text v-on:click="Logout">Logout</v-btn>
                <router-link to="/login">
                    <v-btn v-if="email == '??'" text>Login</v-btn>
                </router-link>
               <!--  <router-link to="/">
                    <v-btn text>Home</v-btn>
                </router-link>
                <router-link to="/airports">
                    <v-btn text>Airports</v-btn>
                </router-link> -->
            </v-toolbar-items>
        </v-layout>
    </v-app-bar>
    <div style="margin-bottom: 30px;"></div>
</div>`,
  props: ["title", "email"],
  $_veeValidate: {
    validator: "new"
  },
  data() {
    return {
      menu: false,
      items: [
        { title: "Home", icon: "home", path: "/" },
        { title: "Airports", icon: "local_airport", path: "/airports" },
        { title: "Cameras", icon: "videocam", path: "/cams" }
      ]
    }
  },
  mounted() { },
  methods: {
    Logout() {
      fbAuth
        .signOut()
        .then(() => {
          // Sign-out successful.
          this.$router.push("/")
        })
        .catch(function (error) {
          // An error happened.
        })
      //console.log('logout', fbAuth.currentUser)
    }
  }
})
