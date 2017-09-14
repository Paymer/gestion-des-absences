export default class HistogrammeController {
    constructor(histogrammeService) {
        this.telecharger = false;
        this.histogrameService = histogrammeService;
        this.histogrameService.getDepartements()
            .then(data => this.departements = data);

        }

 chart (departement, year, month){

     this.telecharger = true;
      this.histogrameService.findData(departement, month, year)
        .then(barChartData => {
   this.ctx = document.getElementById("canvas").getContext("2d");
             new Chart(this.ctx, {
                responsive: true,
                type: 'bar',
                data: barChartData,
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
        })

  
 }

    }
