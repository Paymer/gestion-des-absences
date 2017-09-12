export default class HistogrammeController {
    constructor(histogrammeService) {

        this.histogrameService = histogrammeService;
       // this.data = this.histogrammeService.data;

        this.barChartData = {
             labels: ["December", "January", "February", "March", "April", "May"],
            datasets: [

                {
                    label: "donations",
                    backgroundColor: "yellow",
                    yAxisID: "bar-y-axis",
                    data: [40, 350, 980]
                },

                {
                    label: 'fundraisers',
                    backgroundColor: "#ff6384",
                    yAxisID: "bar-y-axis",
                    data: [3985, 6.4, 1244.5]
                },

                {
                    label: 'shop',
                    backgroundColor: "orange",
                    yAxisID: "bar-y-axis",
                    data: [8910, 12, 120, 50]
                },

                {
                    label: 'Events',
                    backgroundColor: "purple",
                    yAxisID: "bar-y-axis",
                    data: [1705.44, 12221.2, 40.3, 150]
                }



            ]
        };

 
            this.ctx = document.getElementById("canvas").getContext("2d");
            this.window.myBar = new Chart(this.ctx, {
                responsive: true,
                type: 'bar',
                data: this.barChartData,
                options: {
                    title: {
                        display: true,
                        text: "Synthèse par jour"
                    },
                    tooltips: { mode: 'label' },
                    scales: {
                        xAxes: [{ stacked: true }],
                        yAxes: [{
                            id: "bar-y-axis",
                            stacked: true,
                            ticks: {
                                beginAtZero: true,
                                min: 0
                            }
                        }]
                    }
                }
            });

        }



    }
