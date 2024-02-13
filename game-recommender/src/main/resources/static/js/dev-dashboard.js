let jsonDataDOM = document.getElementById("jsonData");
let data = JSON.parse(jsonDataDOM.dataset.jsondata);
console.log(JSON.parse(data.topRatedGameRatings).content)

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
    type: 'value',
    min: Math.floor((100-(100 - Math.min(...JSON.parse(data.topRatedGameRatings).content)*100)*2)/10)*10,
    max: 100
  },
  yAxis: {
    type: 'category',
    data: JSON.parse(data.topRatedGameTitles).content.reverse()
  },
  series: [
    {
      name: 'Ratings (%)',
      type: 'bar',
      stack: 'total',
      color: chartGreen,
      label: {
        show: true
      },
      emphasis: {
        focus: 'series'
      },
      data: JSON.parse(data.topRatedGameRatings).content.reverse().map(x => x * 100).map(x => (Math.round(x * 100) / 100).toFixed(2))
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
    data: JSON.parse(data.topFollowedGameTitles).content.reverse()
  },
  series: [		// TODO
    {
      name: 'Total Followers',
      type: 'bar',
      stack: 'total',
      color: chartBlueDark,
      label: {
        show: true
      },
      emphasis: {
        focus: 'series'
      },
      data: JSON.parse(data.topFollowedFollowerCount).content.reverse()
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
      data: JSON.parse(data.genreCount),
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
    data: JSON.parse(data.pastWeekDayNames)
  },
  yAxis: {
    type: 'value'
  },
  series: [		// TODO
    {
      name: 'Account',
      type: 'line',
      stack: 'Total',
      areaStyle: {},
      data: JSON.parse(data.pastWeekNewAccFollows)
    },
    {
      name: 'Game',
      type: 'line',
      stack: 'Total',
      areaStyle: {},
      data: JSON.parse(data.pastWeekNewGameFollows)
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