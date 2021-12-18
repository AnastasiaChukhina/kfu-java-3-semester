window.addEventListener("DOMContentLoaded", () => {
    let goDown = true;
    let goRight = true;
    let circle = document.getElementById("c1");
    let tg = 1.7;

    circle.onclick = function(){
        let dx = 0;
        let dy = 0;
        let g = 10;
        
        let moveVert = setInterval(function(){
            dy = goDown? dy + g : dy - g;
            if(goDown && circle.getBoundingClientRect().top > document.documentElement.clientHeight - 110)
                goDown = false;
            if (!goDown && circle.getBoundingClientRect().top <= 0)
                goDown = true;
            circle.style.top = dy + "px";          
        }, 1000/40);

        let moveHor = setInterval(function(){
            dx = goRight? dx + g/tg : dx - g/tg;
            if(goRight && circle.getBoundingClientRect().right > document.documentElement.clientWidth - 5)
                goRight = false;
            if (!goRight && circle.getBoundingClientRect().right <= 100)
                goRight = true;
            circle.style.left = dx + "px";          
        }, 1000/40);
    }
})
