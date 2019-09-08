import React, { Component } from 'react';
import { Route, Switch } from 'react-router-dom';
import './App.css';

import ArticlesBody from './components/ArticlesBody/ArticlesBody.js';
import Navbar from './components/Navbar/Navbar.js';
import Help from './components/InfoComponents/Help.js';
import About from './components/InfoComponents/About.js';
import Footer from './components/Footer/Footer.js';
import Article from './components/Article/Article.js';

export default class App extends Component {

    constructor(props){
        super(props);

        this.state = {
            infoList: ['Home','About','Help'],
            categoryList: ['Home','News','Show','Sport','Lifestyle'],
            currentState: 'Home',
            type: 'normal',
            articleHighlight: [],
            article: [],
            id: '',
        };

        this._appGetState = this._appGetState.bind(this);
        this.getArticles();
    }

    executeFetch = (uri) => {
        fetch(uri)
        .then(res => {
            var prom = res.json();
            prom.then(data => {
                console.log("Fetch data: " + data)
                this.setState({articleHighlight: data})
            })
        })
        .catch(console.log)
    }

    getArticles = () => {
        var uri = 'http://10.0.2.15:31234/article/recent';

        console.log("Executing the rest query at: " + uri);
        this.executeFetch(uri);
    }

    getCategoryArticles = () => {
        var uri = 'http://10.0.2.15:31234/article/recent';

        if(this.state.currentState !== '' && this.state.currentState !== "Home") {
            uri = uri + 'Category?category=' + this.state.currentState;
        }

        console.log("Executing the rest query at: " + uri);
        this.executeFetch(uri);
    }

    setArticleId = (articleId) => {
        this.setState({id: articleId});
    }

    _appGetState(description){
        this.setState({currentState: description});
        this.state.categoryList.map((item, key) => {
                this.getCategoryArticles();
        });
        
    }

    render() {
        return(
            <div id="App" className={this.state.type}>
                <Navbar appGetState={this._appGetState} stateProp={this.state} />
                <Switch>
                    <Route exact  path='/' render={ () => <ArticlesBody stateProp={this.state} onArticleClick={this.setArticleId} />} />
                    <Route path='/Home' render={ () => <ArticlesBody stateProp={this.state} onArticleClick={this.setArticleId} />} />
                    <Route path='/News' render={ () => <ArticlesBody stateProp={this.state} onArticleClick={this.setArticleId} />} />
                    <Route path='/Show' render={ () => <ArticlesBody stateProp={this.state} onArticleClick={this.setArticleId} />} />
                    <Route path='/Sport' render={ () => <ArticlesBody stateProp={this.state} onArticleClick={this.setArticleId} />} />
                    <Route path='/Lifestyle' render={ () => <ArticlesBody stateProp={this.state} onArticleClick={this.setArticleId} />} />
                    <Route path='/Help'  render={ () => <Help appGetState={this._appGetState} stateProp={this.state} /> } />
                    <Route path='/About'  render={ () => <About appGetState={this._appGetState} stateProp={this.state} /> } />
                    <Route path="/Article" render={ () => <Article appGetState={this._appGetState} stateProp={this.state} />}/>
                </Switch>
                <Footer appGetState={this._appGetState} stateProp={this.state} />
            </div> 
        );
    }
}