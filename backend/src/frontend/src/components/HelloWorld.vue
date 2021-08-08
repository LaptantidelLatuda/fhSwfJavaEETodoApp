<template>
<p> {{msg}} </p>
    <h1 margin-left="3em"> ToDo App </h1>
    <!-- das submit-Ereigniss wird die Seite nicht mehr neu-->
    <form @submit.prevent="addTodo()">
      <input type="text" id="newTodoTitel" ref="searchbar" v-model="newTodoTitel" name="newTodoTitel" placeholder="Enter titel of your new ToDo-Task" autocomplete="off" ><br>
      <textarea id ="newTodoDescription" rows="5" cols="40"  v-model="newTodoDescription" name="newTodoDescription" placeholder="Enter description of your new ToDo-Task" autocomplete="off" ></textarea><br>
      <input id = "newTodoDueDate" type="date" min="2021-08-03" max ="2040-01-01" v-model="newTodoDueDate" name="newTodoDueDate"><br>
      <button  class="buttonPlus" >Add New ToDo-Task</button><br>
    </form >
    <h1>ToDo List</h1>
    <table> 
      <thead>
        <tr>
          <th v-for="col in (columns)" :key= "col.id" v-on:click="sortTable(col)">
            <p v-if="col !== 'edit'">{{col}}</p>
              <div class="arrow" v-if="col == sortColumn" v-bind:class="ascending ? 'arrow_down' : 'arrow_up'"></div>
            
          </th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="row in todos" :key="row.id">
          <td v-for="col in columns" :key="col.id" ><p v-if="col !== 'edit'">{{row[col]}}</p>
          <button  v-if="col === 'actions'" class="button"  @click="isDone(row.id)">Done</button>
          <button v-if="col === 'actions'" class="button"  @click="removeTodo(row.id)">Delete</button>
          <button  v-if="col === 'actions'" class="button" @click="editTodo(row.id)">{{row.edit}}</button>
          </td>
        </tr>
      </tbody>
</table>
    <h4 v-if="todos.length === 0">Empty list.</h4>
    <!--<button @click= "showText =!showText"> Toggle Text</button>
    <label for ="name"> Name: </label>
    <input type = "text" id ="name" name ="name" v-model="name"/>
    <p v-show="name != ''"> Hello, {{name}}</p>
    <p v-show="showText"> this is some Text on my page. </p>
    <ul>
      <li v-for="(item, index) in items" :key="item.message">
        {{ index }} {{ item.message }}
      </li>
    </ul>-->
</template>

<script>
import axios from 'axios'

export default {
  name: 'HelloWorld',
  data(){
    const defaultData = [
                          { id: 0, titel: "ToDo-App", description: 'Create a todo-App for the JEE module', dueDate: '04.09.2021',isDone : false, actions : '', edit : 'Edit' },
                          { id: 1, titel: "VueJS", description: 'Find information, read about usage, try it yourself ', dueDate: '14.08.2021',isDone : false, actions : '',edit : 'Edit'},
                          { id: 2, titel: "Spring Boot", description: 'Find information, read about usage, try it yourself ', dueDate: '14.08.2021',isDone : false,actions : '',edit : 'Edit'}
                        ];
    
    // Zuweisung von dem Wert funktioniert nicht ...
    var getDate = new Date();
    var newDay = getDate.getFullYear();
    if ((getDate.getMonth()+1) < 10){
      newDay  = newDay + '-' + '0' + (getDate.getMonth()+1);
    } else newDay  = newDay + '-' + (getDate.getMonth()+1);
    if (getDate.getDate() < 10) {
        newDay  = newDay + '-' + '0' + (getDate.getDate());
    } else newDay  = newDay + '-' + (getDate.getDate());


    return {
      //count: 0,
      msg: '',
      //showText : true, 
      //name: '',
      ascending: true,
      sortColumn: 'id',
      editing : false,
      edit : 'Edit',
      todos: defaultData,
      newTodoTitel : '',
      newToDoEntry: {
        titel : '',
        description : '',
        dueDate : '',
        isDone: ''
      },
      newTodoDescription : '',
      newTodoDueDate : '',
      today : newDay
    }
  },
  computed: {
    "columns": function columns() {
      if (this.todos.length == 0) {
        return [];
      }
      return Object.keys(this.todos[0])
    }
  },
  mounted(){
    this.getEntries()
  },
 /*  mounted() {
     fetch("/api/messages/hello")
              .then((response) => response.text())
              .then((data) => { this.msg=data });
     
     //this.$refs.searchbar.$el.focus();
     
  } ,*/
  methods :{
          "sortTable": function sortTable(col) {
                          if (this.sortColumn === col) {
                            this.ascending = !this.ascending;
                          } else {
                            this.ascending = true;
                            this.sortColumn = col;
                          }

                          var ascending = this.ascending;

                          this.todos.sort(function(a, b) {
                            if (a[col] > b[col]) {
                              return ascending ? 1 : -1
                            } else if (a[col] < b[col]) {
                              return ascending ? -1 : 1
                            }
                            return 0;
                          })
    },
    getEntries(){
      axios
      .get("api/messages/hello")
      .then(response =>{
        //var myjson = response.data;
        /*var myjson = {{ "id": "0", "titel": "ToDo-App", "description": 'Create a todo-App for the JEE module', "dueDate": '04.09.2021',"isDone" : "false" },
                      { "id": "1", "titel": "VueJS", "description": 'Find information, read about usage, try it yourself ', "dueDate": '14.08.2021',"isDone" : "false"},
                      { "id": "2", "titel": "Spring Boot", "description": 'Find information, read about usage, try it yourself ', "dueDate": '14.08.2021',"isDone" : "false"}}; */
        //console.log(JSON.parse(myjson));
       /*var myobject = JSON.parse(myjson)
        this.msg =  myobject; */
        this.msg = response.data;
        })
    },
    editTodo(index) {
      
      // this.newTodoTitel enthält den neuen Text
      // index enthält die id, an der der Eintrag in der DB gelöscht werden soll

      this.editing = !this.editing;

      if(this.editing){
        this.todos[this.todos.findIndex(e =>e.id===index)].edit = "Save";
        this.newTodoTitel = this.todos[this.todos.findIndex(e =>e.id===index)].titel;
        this.newTodoDescription = this.todos[this.todos.findIndex(e =>e.id===index)].description;

        var dateFormatEntries =  this.todos[this.todos.findIndex(e =>e.id===index)].dueDate.split('.');
        this.newTodoDueDate = dateFormatEntries[2] + "-" + dateFormatEntries[1]+ "-" + dateFormatEntries[0];
      } else {
        this.todos[this.todos.findIndex(e =>e.id===index)].edit = "Edit";
        this.saveChanges(this.todos.findIndex(e =>e.id===index));
        this.editing = !this.editing;
      }
     
      
    },
    saveChanges(index){
      if (this.newTodoDueDate > this.today){
        var dateFormatEntries =  this.newTodoDueDate.split('-');
        var dateFormat = dateFormatEntries[2] + "." + dateFormatEntries[1]+ "." + dateFormatEntries[0];

        this.newToDoEntry.titel = this.newTodoTitel;
        this.newToDoEntry.description = this.newTodoDescription;
        this.newToDoEntry.dueDate = dateFormat;
        this.newToDoEntry.isDone = this.todos[index].isDone;

        axios
        .put("api/messages/todo/" + this.todos[index].id , this.newToDoEntry)

        console.log("Edit: newtitel =" + this.newTodoTitel+ " + newdescription = "+ this.newTodoDescription+ " + newduedate = " + dateFormat);
        console.log(this.todos[index].id);
      } else console.log(" No entries for past time allowed ")
      
      this.editing = !this.editing;

      this.newTodoTitel =  '',
      this.newTodoDescription = '',
      this.newTodoDueDate = ''

      console.log()
    },
    addTodo () {
      if (this.newTodoTitel&&this.newTodoDescription&&this.newTodoDueDate){
        console.log(this.today);
        if (this.newTodoDueDate > this.today){
          var dateFormatEntries =  this.newTodoDueDate.split('-');
          var dateFormat = dateFormatEntries[2] + "." + dateFormatEntries[1]+ "." + dateFormatEntries[0];
          console.log("new Entry :"+ this.newTodoTitel+" "+this.newTodoDescription+" "+dateFormat);

          this.newToDoEntry.titel = this.newTodoTitel;
          this.newToDoEntry.description = this.newTodoDescription;
          this.newToDoEntry.dueDate = dateFormat;
          this.newToDoEntry.isDone = 'false';

          axios
            .post("/api/messages/todo",this.newToDoEntry)
            .then((response)=>{
              console.log(response);
            })
            .catch((error)=>console.log(error));


          this.newTodoTitel =  '',
          this.newTodoDescription = '',
          this.newTodoDueDate = ''
          //this.newTodoTitel enthält den neuen Text

        /* this.todos.push({
          id: this.todos.length,
          titel: this.newTodoTitel,
          description: this.newTodoDescription,
          dueDate: this.newTodoDueDate,
          isDone: false 
        }); */
        } else console.log(" No entries for past time allowed ")
      }
      
    },
    removeTodo (index) {

      axios
      .delete("api/messages/todo/"+index);
            
      console.log(index);
      // index enthält die id, an der der Eintrag in der DB gelöscht werden soll

      //this.todos.splice(index, 1);
    }, 
    isDone(index){
      // finde die richtige id zum index und verändere dort den Wert
      this.todos[this.todos.findIndex(e =>e.id===index)].isDone = !this.todos[this.todos.findIndex(e =>e.id===index)].isDone;
      console.log(index + " todo is isDone " + this.todos[this.todos.findIndex(e =>e.id===index)].isDone);
      
    }
  }
}


</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style>
  table {
  font-family: 'Open Sans', sans-serif;
  width: 80%;
  border-collapse: collapse;
  border: 2px solid #221B4D;
  margin: 10px 10px 0 10px;
}

table th {
  text-transform: uppercase;
  text-align: left;
  background: #656085;
  color: #FFF;
  padding: 8px;
}
table th:hover {
  background: #717699;
}
table td {
  text-align: left;
  padding: 8px;
  min-width: 50px;
}

table tbody tr:nth-child(2n) td {
  background: #C1BDDD;
}

label {
  font-family: 'Open Sans', sans-serif;
  min-width: 30px;
  padding: 8px;
}
h1,form{
  padding : 16px;
}
input[type=text]:focus {
  background-color: lightgrey;
  font-family: 'Open Sans', sans-serif;
  padding: 12px 20px;
  margin: 8px 0;
  box-sizing: border-box;
  width: 30%;
  border: none;
  border-bottom: 2px solid black;
}
input[type=text] {
  font-family: 'Open Sans', sans-serif;
  padding: 12px ;
  margin: 8px 0;
  box-sizing: border-box;
  width: 30%;
  border: none;
  border-bottom: 2px solid black;
}
input[type=date] {
  font-family: 'Open Sans', sans-serif;
  padding: 12px ;
  margin: 8px 0;
  width: 15%;
}
textarea{
  font-family: 'Open Sans', sans-serif;
  padding: 12px ;
  margin: 8px 0;
  box-sizing: border-box;
  width: 30%;
  border: none;
  border-bottom: 2px solid black;
}
.button {
  background-color: #327DA7;
  color: white;
  height: 50px;
  width: 100px;
  padding: 10px 10px;
  text-align: center;
  font-size: 16px;
  margin: 2px;
  opacity: 0.6;
  text-decoration: none;
  cursor: pointer;
}

.button:hover {opacity: 1}

.buttonPlus {
  background-color: #4C9F78;
  color: white;
  height: 50px;
  width: 170px;
  padding: 10px 10px;
  text-align: center;
  font-size:16px;
  margin: 30px 0px;
  opacity: 0.6;
  text-decoration: none;
  cursor: pointer;
}

.buttonPlus:hover {opacity: 1}

.arrow_down {
  background-image: url('data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAB8AAAAaCAYAAABPY4eKAAAAAXNSR0IArs4c6QAAAvlJREFUSA29Vk1PGlEUHQaiiewslpUJiyYs2yb9AyRuJGm7c0VJoFXSX9A0sSZN04ULF12YEBQDhMCuSZOm1FhTiLY2Rky0QPlQBLRUsICoIN/0PCsGyox26NC3eTNn3r3n3TvnvvsE1PkwGo3yUqkkEQqFgw2Mz7lWqwng7ztN06mxsTEv8U0Aam5u7r5EInkplUol/f391wAJCc7nEAgE9Uwmkzo4OPiJMa1Wq6cFs7Ozt0H6RqlUDmJXfPIx+qrX69Ti4mIyHA5r6Wq1egND+j+IyW6QAUoul18XiUTDNHaSyGazKcZtdgk8wqhUKh9o/OMvsVgsfHJy0iWqVrcQNRUMBnd6enqc9MjISAmRP3e73T9al3XnbWNjIw2+KY1Gc3imsNHR0YV4PP5+d3e32h3K316TySQFoX2WyWR2glzIO5fLTSD6IElLNwbqnFpbWyO/96lCoai0cZjN5kfYQAYi5H34fL6cxWIZbya9iJyAhULBHAqFVlMpfsV/fHxMeb3er+Vy+VUzeduzwWC45XA4dlD/vEXvdDrj8DvURsYEWK3WF4FA4JQP9mg0WrHZbEYmnpa0NxYgPVObm5teiLABdTQT8a6vrwdRWhOcHMzMzCiXlpb2/yV6qDttMpkeshEzRk4Wo/bfoe4X9vb2amzGl+HoXNT29vZqsVi0sK1jJScG+Xx+HGkL4Tew2TPi5zUdQQt9otPpuBk3e0TaHmMDh1zS7/f780S0zX6Yni+NnBj09fUZUfvudDrNZN+GkQbl8Xi8RLRtHzsB9Hr9nfn5+SjSeWUCXC7XPq5kw53wsNogjZNohYXL2EljstvtrAL70/mVaW8Y4OidRO1/gwgbUMvcqGmcDc9aPvD1gnTeQ+0nmaInokRj0nHh+uvIiVOtVvt2a2vLv7Ky0tL3cRTXIcpPAwMDpq6R4/JXE4vFQ5FI5CN+QTaRSFCYc8vLy1l0rge4ARe5kJ/d27kYkLXoy2Jo4C7K8CZOsEBvb+9rlUp1xNXPL7v3IDwxvPD6AAAAAElFTkSuQmCC')
}
.arrow_up {
  background-image: url('data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAB4AAAAaCAYAAACgoey0AAAAAXNSR0IArs4c6QAAAwpJREFUSA21Vt1PUmEYP4dvkQ8JFMwtBRocWAkDbiqXrUWXzU1rrTt0bdVqXbb1tbW16C9IBUSmm27cODdneoXjputa6069qwuW6IIBIdLvdaF4OAcOiGeDc87zPs/vd57P96WpFq7p6enbGo1mjKZpeTabjU1MTCRagGnOZHFxcXxtbe1XKpUq7+zslJeXl//Mz8+Hy+Uy3RxSE9qTk5M3otFooVQqgef4Wl9f343FYoEmoISrxuNxFX5f9vb2jhn/PxUKhfLS0tIPfFifUESRUMV8Pv/M6XReRm5rTGQyGeXxeGxYe1ezeBpBOBx2rKysbO7v79d4Wy3Y2Nj4GQqFbgnhaugxwiuGJx99Pp9FLBbXxYTXvTqd7v3MzIy6riIWGxJnMpl7AwMD14xGYyMsSq1WUyQdUqn0eSPlusQIsbGrq+vl4OCgvhFQZd1utyv1en0gEolcqsi47nWJlUrlG5fLZVcoFFy2nDKSDpIWlUoVTCQSEk4lCHmJMZ2GTCbTiMVikfIZ88l7enoos9l8dXt7+z6fDicxSJUokqDX6xXcl2wCROoc0vQCWL3sNfLOSdzR0fHY4XC4tVotl40gmVwup9xuN4OQv+UyqCFGH9rg7SOGYVRcBs3IEG4J0nVnamrqOtvuBDGGgQg9+wHFcVEi4a0LNkbdd6TrPKo8ODc311mteIIYjT/a398/jK+s1jnVM0kXoufCFvq0GuiIGEVgQIhfoygM1QrteEa9dAL7ITiYCt4RMabOK5AyKKzKWtvupLcRciu8D5J0EuDDPyT/Snd39yh6VtY2NhYQSR9G79Ds7OxdskRjEyAufvb7/cPoO5Z6e1+xtVKrq6vfcFzyi/A3ZrPZ3GdNSlwgo5ekE4X2RIQGf2C1WlufFE0GBeGWYQ8YERWLxQtnUVB830MKLZfL9RHir8lkssCn2G751tZWEWe03zTKm15YWPiEiXXTYDB0Ig/t7yd8PRws4EicwWHxO4jHD8/C5HiTTqd1BwcHFozKU89origB+y/kmzgYpgOBQP4fGmUiZmJ+WNgAAAAASUVORK5CYII=')
}
.arrow {
  float: right;
  width: 12px;
  height: 15px;
  background-repeat: no-repeat;
  background-size: contain;
  background-position-y: bottom;
}

</style>