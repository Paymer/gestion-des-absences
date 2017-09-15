export default class HistogrammeController {
    constructor(histogrammeService, $location, frontUrls) {
        this.telecharger = false;
        this.$location = $location
        this.frontUrls = frontUrls
        this.histogrameService = histogrammeService;
        this.histogrameService.getDepartements()
            .then(data => this.departements = data);

        }

 chart (departement, year, month){

     this.telecharger = true;
      this.histogrameService.findData(departement, month, year)
        .then(barChartData => {
            const ctx = document.getElementById("canvas").getContext("2d");
            
            if(this.chartA) {
              
                this.chartA.data.labels = barChartData.labels
                this.chartA.data.datasets = barChartData.datasets
                this.chartA.update();
            } else {
            this.chartA = new Chart(ctx, {
                responsive: true,
                type: 'bar',
                data: barChartData,
                options: {
                    legend:{
                        display:true,
                        label: barChartData.datasets.label
                    },
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
                                min: 0,
                                max: this.getNumberMax(barChartData)
                            }
                        }]
                    }
                }
            });
            }
            
           
        })


 }

  getNumberMax (barChartData){
      let i = 0;
    barChartData.datasets.forEach((element) =>{
        i=i+1;
    }, this);
        i=i+1;
      return i;
  }


  rediriger(a) {
		
			this.$location.path(this.frontUrls.rapport)
			
}

    }
