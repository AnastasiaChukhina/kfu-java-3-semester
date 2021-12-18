window.addEventListener('DOMContentLoaded', () => {
    let num = 40;
    let maxSize = 48;
    let minSize = 24;
    let speed = 0.015;
    let color = new Array("#bad5dc","#e3e8ee","#d4dfe5","#a5b4b9");
    let snowflake = new Array("&#10052;", "&#10053;", "&#10054;");
    let mBottom = document.documentElement.clientHeight;
    let mRight = document.documentElement.clientWidth;
    let snow = new Array();
    let dx = new Array();
    let rad = new Array();
    let shift = new Array();

    for (i = 0; i <= num; i++) {
        let k = i % (snowflake.length);
        document.write("<span id='snowflake"+ i +"' style='position:absolute;" + maxSize + "'>" + snowflake[k] + "</span>");
    }
    
    for (i = 0; i <= num; i++) {
        rad[i] = 0;
        shift[i] = Math.random()*10;
        dx[i] = Math.random()/5;
        snow[i] = document.getElementById("snowflake"+i);

        setParams(snow[i]);
    }
    fall();

    function fall() {
        for (i = 0; i <= num; i++) {
            rad[i] += dx[i];
            snow[i].style.left = snow[i].posx + shift[i] * Math.cos(rad[i]);
            snow[i].style.top = snow[i].posy += snow[i].speed;

            if (snow[i].posy >= mBottom - snow[i].size)
                refresh(snow[i]);
        }
        setTimeout(fall, 1000/24);
    }

    function setParams(elem){
        elem.size=random(maxSize - minSize) + minSize;
        elem.style.fontSize = snow[i].size;
        elem.style.color = color[random(color.length)];
        elem.style.zIndex = -1;
        elem.speed = speed * snow[i].size;
        elem.style.left = snow[i].posx= random(mRight-snow[i].size);
        elem.style.top = snow[i].posy = random(mBottom - snow[i].size);
    }

    function refresh(elem){
        elem.posx = random(mRight-snow[i].size);
        elem.posy=0;
    }

    function random(range) {
        return Math.floor(range*Math.random());
    }
})
