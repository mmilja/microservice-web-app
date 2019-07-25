import React, { Component } from 'react';
import Base64 from 'base-64';
import './Article.css';

export default class Article extends Component {

    constructor(props){
        super(props);
        
        this.state = this.props.stateProp;
    }

    componentDidMount() {
        let restHeaders = new Headers();
        var uri = 'http://backend/article/' + this.state.articleHighlight.title;
        restHeaders.set('Authorization', 'Basic ' + Base64.encode("Database:Access"));


        fetch(uri, {
            method: 'GET',
            headers: restHeaders})
        .then(res => res.json())
        .then((data) => {
            this.setState({article: data})
        })
        .catch(console.log)
    }

    render() {
     return (
        <div class="article">
            <div class="article-image">
                <img src={`data:image/jpeg;base64,${this.state.article.image}`} alt="Article" />
            </div>
            <div class="article-data">
                <div class="article-title">
                    <h1>{this.state.article.title}</h1>
                </div>
                <div class="article-metadata">
                        <span className="category">{this.state.article.category}</span>
                        <span className="time">{this.state.article.time}</span>
                </div>
                <div class="article-meta">
                    <p class="article-text">
                        {this.state.article.content}
                    </p>
                </div>
            </div>

            <div class="article-author">
                <div class="author-info">
                    <a href="#" class="author-name">{this.state.article.author}, <span>The Author</span></a>
                </div>
            </div>
        </div>
     )
    }
}