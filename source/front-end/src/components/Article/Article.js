import React, { Component } from 'react';
import './Article.css';

export default class Article extends Component {

    constructor(props){
        super(props);

        this.state = {
            article: []
        }

        console.log(this.props)

        let id = this.props.stateProp.id;

        var uri = 'http://10.0.2.15:31234/article/' + id;

        console.log("Executing the rest query at: " + uri)

        fetch(uri)
        .then(res => {
            var prom = res.json();
            prom.then(data => {
                this.setState({article: data});
            })
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
                    {this.state.article.content}
                </div>
            </div>

            <div class="article-author">
                <div class="author-info">
                    <p class="author-name">{this.state.article.author}, <span>The Author</span></p>
                </div>
            </div>
        </div>
     )
    }
}