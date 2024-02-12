// https://echarts.apache.org/examples/en/index.html
// Colors
let chartRed = '#ee6666'
let chartGreen = '#91cc75'
let chartBlueLight = '#73c0de'
let chartBlueDark = '#5470c6'
let chartYellow = '#fac858'

// Top Ratings 
let chartTopRatings = document.getElementById('chart-top-ratings');
let eChartTopRatings = echarts.init(chartTopRatings);
let optionTopRatings = {
  title: {
    text: 'Top Games',
    subtext: '(by Ratings)'
  },
  tooltip: {
    trigger: 'axis',
    axisPointer: {
      type: 'shadow'
    }
  },
  legend: {
	 orient: 'horizontal',
	 right: 0
  },
  grid: {
	top: '25%',
    bottom: '0%',
    left: '5%',
    right: '10%',
    containLabel: true
  },
  xAxis: {
    type: 'value'
  },
  yAxis: {
    type: 'category',
    data: ['Game3', 'Game2', 'Game1']		// TODO
  },
  series: [		// TODO
    {
      name: 'Positive',
      type: 'bar',
      stack: 'total',
      color: chartGreen,
      label: {
        show: true
      },
      emphasis: {
        focus: 'series'
      },
      data: [301, 334, 390]
    },
    {
      name: 'Negative',
      type: 'bar',
      stack: 'total',
      color: chartRed,
      label: {
        show: true
      },
      emphasis: {
        focus: 'series'
      },
      data: [101, 134, 90]
    }
  ]
};

// Top Follow Count 
let chartTopFollowers = document.getElementById('chart-top-followers');
let eChartTopFollowers = echarts.init(chartTopFollowers);
let optionTopFollowers = {
  title: {
    text: 'Top Games',
    subtext: '(by Follower Count)'
  },
  tooltip: {
    trigger: 'axis',
    axisPointer: {
      type: 'shadow'
    }
  },
  legend: {
	 orient: 'horizontal',
	 right: 0
  },
  grid: {
    top: '25%',
    bottom: '0%',
    left: '5%',
    right: '10%',
    containLabel: true
  },
  xAxis: {
    type: 'value'
  },
  yAxis: {
    type: 'category',
    data: ['Game3', 'Game2', 'Game1']		// TODO
  },
  series: [		// TODO
    {
      name: 'Past',
      type: 'bar',
      stack: 'total',
      color: chartBlueDark,
      label: {
        show: true
      },
      emphasis: {
        focus: 'series'
      },
      data: [20, 32, 31, 34, 39]
    },
    {
      name: 'Recent',
      type: 'bar',
      stack: 'total',
      color: chartBlueLight,
      label: {
        show: true
      },
      emphasis: {
        focus: 'series'
      },
      data: [20, 20, 50, 20, 30]
    }
  ]
};

// Genres Distribution 
let chartGenresDist = document.getElementById('chart-genres-dist');
let eChartGenresDist = echarts.init(chartGenresDist);
let optionGenresDist = {
  title: {
    text: 'Game Genres',
    left: 'left'
  },
  tooltip: {
    trigger: 'item'
  },
  legend: {
    orient: 'vertical',
    right: 'center',
    bottom: 'bottom'
  },
  series: [
    {
      type: 'pie',
      radius: '50%',
      y: '0%',
      stillShowZeroSum: false,
      data: [		// TODO
        { value: 1048, name: 'FPS' },
        { value: 735, name: 'RTS' },
        { value: 580, name: 'Casual' },
        { value: 484, name: 'RPG' },
        { value: 300, name: 'Single Player' }
      ],
      emphasis: {
        itemStyle: {
          shadowBlur: 10,
          shadowOffsetX: 0,
          shadowColor: 'rgba(0, 0, 0, 0.5)'
        }
      }
    }
  ]
};

// New Followers (Dev/Game) 
let chartNewFollows = document.getElementById('chart-new-follows');
let eChartNewFollows = echarts.init(chartNewFollows);
let optionNewFollows = {
  title: {
    text: 'New Follows'
  },
  tooltip: {
    trigger: 'axis'
  },
  legend: {
    bottom: 'bottom',
    icon: 'roundRect'
  },
  grid: {
    top: '20%',
    bottom: '15%',
    left: '5%',
    right: '10%',
    containLabel: true
  },
  xAxis: {
    type: 'category',
    boundaryGap: false,
    data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun']		// TODO
  },
  yAxis: {
    type: 'value'
  },
  series: [		// TODO
    {
      name: 'Account',
      type: 'line',
      stack: 'Total',
      data: [120, 132, 101, 134, 90, 230, 210]
    },
    {
      name: 'Game',
      type: 'line',
      stack: 'Total',
      data: [220, 182, 191, 234, 290, 330, 310]
    }
  ]
};

// Setup Charts
eChartTopRatings.setOption(optionTopRatings);
eChartTopFollowers.setOption(optionTopFollowers);
eChartGenresDist.setOption(optionGenresDist);
eChartNewFollows.setOption(optionNewFollows);

// Dynamically Resize Charts
window.addEventListener('resize', function() {
	eChartTopRatings.resize();
	eChartTopFollowers.resize();
	eChartGenresDist.resize();
	eChartNewFollows.resize();
});