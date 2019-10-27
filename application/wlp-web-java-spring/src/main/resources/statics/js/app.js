function changeColor() {
   var title = document.getElementById('title');
   if (title.className == 'color1') {
   	title.className = 'color2';
   } else {
   	title.className = 'color1';
   }
}