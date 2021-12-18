document.addEventListener('DOMContentLoaded', function(){
        i = 0;
        colors = ['red', 'green', 'blue'];

        document.getElementById('orangeButton').onclick = function(){
            document.body.style.backgroundColor = 'red';
        };
        
        document.getElementById('rgbButton').onclick = function(){
            document.body.style.backgroundColor = colors[i++ % colors.length];
        };

        document.getElementById('skyButton').onclick = function(){
            document.body.style.backgroundColor = window.getComputedStyle(this).backgroundColor
        };
        
        document.getElementById('blueButton').onclick = function(){
            document.body.style.backgroundColor = window.getComputedStyle(this).backgroundColor
        };
        
        document.getElementById('violetButton').onclick = function(){
            document.body.style.backgroundColor = window.getComputedStyle(this).backgroundColor
        };
    })