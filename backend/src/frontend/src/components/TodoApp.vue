<template>
    <h1>ToDo List</h1>
    <table> 
      <thead>
        <tr>
          <th v-for="col in (columns)" :key= "col.idTodo" v-on:click="sortTable(col)">
            <p v-if="col !== 'edit'">{{col}}</p>
              <div class="arrow" v-if="col == sortColumn" v-bind:class="ascending ? 'arrow_down' : 'arrow_up'"></div>
            
          </th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="row in get_rows()" :key="row.idTodo">
          <td v-for="col in columns" :key="col.idTodo" ><p v-if="col !== 'isDone' && col !=='edit'">{{row[col]}}</p>
          <button  v-if="col === 'isDone'" class="button" v-bind:class="row.isDone ? 'green' : 'white'" @click="isDone(row.idTodo)"></button>
          <button v-if="col === 'actions'" class="button"  @click="removeTodo(row.idTodo)">Delete</button>
          <button  v-if="col === 'actions'" class="button" @click="editTodo(row.idTodo)">{{row.edit}}</button>
          </td>
        </tr>
      </tbody>
    </table>
    <div class="pagination">
      <div class="number" v-for="i in num_pages()" :key ="i" v-bind:class="[i == currentPage ? 'active' : '']" v-on:click="change_page(i)">{{i}}</div>
    </div>
    <h4 v-if="todos.length === 0">Empty list.</h4>
    <h1 margin-left="3em"> ToDo App </h1>
    <!-- das submit-Ereigniss wird die Seite nicht mehr neu-->
    <form  @submit.prevent="addTodo()">
      <input type="text" idTodo="newTodoTitel" ref="searchbar" v-model="newTodoTitel" name="newTodoTitel" placeholder="Enter titel of your new ToDo-Task" autocomplete="off" >
      <textarea idTodo ="newTodoDescription" rows="10" cols="40"  v-model="newTodoDescription" name="newTodoDescription" placeholder="Enter description of your new ToDo-Task" autocomplete="off" ></textarea>
      <input idTodo = "newTodoDueDate" type="date" min="2021-08-13" max ="2040-01-01" v-model="newTodoDueDate" name="newTodoDueDate"><br>
      <button  class="buttonPlus"  >Add New ToDo-Task</button>
    </form >

</template>

<script src= '@/script/todo.js'> </script>

<style scoped src ='@/style/todo.css'> </style>