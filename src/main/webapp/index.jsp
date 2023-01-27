<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://www.w3schools.com/w3css/3/w3.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.6.3/css/font-awesome.min.css">
<title>Welcome ChessBook Game</title>
<body>

<!-- Slide Show -->
<section>
  <img class="slides" src="images/xadrez1.jpg"
  style="width:100%; height:550px">
  <img class="slides" src="images/xadrez2.jpg"
  style="width:100%; height:550px">
  <img class="slides" src="images/xadrez3.png"
  style="width:100%; height:550px">
</section>

<section class="w3-container w3-center w3-content" style="max-width:600px">
  <h2 class="w3-wide">Welcome To The ChessBook Game Page</h2>
  <p class="w3-opacity"><i>We Love Play</i></p>
  <p class="w3-justify">
  	<h2>If You Want To Play, Please Login <a href="login"><strong>Here</strong></a></h2>
  </p>
 </section>
 
<section class="w3-row-padding w3-center w3-light-grey" style="color:black">
  <article class="w3-third">
    <p>LUCAS PINTO</p>
    <p>56926</p>
  </article>
  <article class="w3-third">
    <p>MARCOS LEITÃO</p>
    <p>55852</p>
  </article>
  <article class="w3-third">
    <p>PEDRO ALMEIDA</p>
    <p>56897</p>
  </article>
</section>

<%@ include file="components/footer.jsp" %>

<script>
// Automatic Slideshow - change image every 3 seconds
var index = 0;
slideshow();

function slideshow() {
  var i;
  var x = document.getElementsByClassName("slides");
  for (i = 0; i < x.length; i++) {
     x[i].style.display = "none";
  }
  index++;
  if (index > x.length) {
	  index = 1
  } 
  x[index-1].style.display = "block";
  setTimeout(slideshow, 3000);
}
</script>

</body>

</html>