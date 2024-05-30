var xValues = [2013, 2014, 2015, 2016, 2017, 2018];
var yValues = [95, 100, 60, 130, 150, 180];
var yValues2 = [-20, 40, 0, 60, 150, 210];
var yValues3 = [-10, 30, 50, 70, 150, 250];

new Chart("myChart", {
    type: "line",
    data: {
        labels: xValues,
        datasets: [{
            label: "Projects",
            fill: false,
            lineTension: 0,
            backgroundColor: "#009BC7",
            borderColor: "#009900",
            data: yValues,
            cubicInterpolationMode: 'monotone'

        },
        {
            label: "Projects",
            fill: false,
            lineTension: 0,
            backgroundColor: "#a3a3c2",
            borderColor: "#0073e6",
            data: yValues2,
            cubicInterpolationMode: 'monotone'

        },
        {
            label: "Projects",
            fill: false,
            lineTension: 0,
            backgroundColor: "#009BC7",
            borderColor: "#ffc61a",
            data: yValues3,
            cubicInterpolationMode: 'monotone'

        }
        ]
    },
    options: {
        legend: { display: true },
        plugins: { legend: { position: 'right', display: false } },
        scales: {
            yAxes: [{ gridLines: { display: true, drawBorder: true } }],
            xAxes: [{ gridLines: { display: false, drawBorder: false } }],
        }
    }
});

const data = {
    datasets: [{
        label: 'My First Dataset',
        data: [370, 500, 700],
        borderWidth: 0,
        backgroundColor: [
            '#ffc61a',
            '#0073e6',
            '#009900'

        ],
        hoverOffset: 4
    }]
};
new Chart("pieChart", {
    type: "doughnut",
    data: data,
    options: { cutout: 120, responsive: false }
});