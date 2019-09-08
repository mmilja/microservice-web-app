import React, { Component } from 'react';
import './ArticleHighlight.css';
import { Link } from 'react-router-dom'


export default class ArticleHighlight extends Component {

    constructor(props){
        super(props);
        
        this.state = {
            article : this.props.articleProp
        } 
    }

    handleIdChange = () => {
        var id = this.state.article.id
        this.props.onArticleClick(id)
    }

    render() {
    return (
        <div className="ArticleHighlight">
            <div className="article-image">
                <Link to={{ pathname: "/Article", search: `?id=${this.state.article.id}`, state: this.state.article.id }}  onClick={this.handleIdChange} ><img src={`data:image/jpeg;base64,${this.state.article.image}`} alt="Article" /></Link>
            </div>
            <div className="article-title">
                <Link to={{ pathname: "/Article", search: `?id=${this.state.article.id}`, state: this.state.article.id }} onClick={this.handleIdChange} ><h1>{this.state.article.title}</h1></Link>
            </div>
        </div>
    )
    }
}