import React, { useEffect, useState } from 'react';
import Navbar from "../../components/Navbar";
import PesquisaCard from './pesquisaCard';
import { researchFeatures } from '../../types/ResearchTypes';
import api from '../../config/axiosConfig';


const ListaPesquisas: React.FC = () => {
  const [research, setResearch] = useState<researchFeatures[]>([]);
  
  const fetchResearchFeatures = async () => {
    try {
      const response = await api.get('/research/feature-all');
      console.log(response.data)
      setResearch(response.data);
    } catch (error) {
      console.error('Erro ao buscar pesquisas:', error);
    }
  };

  useEffect(() => {
    fetchResearchFeatures();
  }, []);

  const estiloCabecalho: React.CSSProperties = {
    fontFamily: 'Montserrat-Bold',
    marginTop: '5vw',
    backgroundImage: 'linear-gradient(to top, #028275, #126b56)',
    backgroundClip: 'text',
    color: 'transparent',
    fontSize: '3vw',
    textAlign: 'center',
    marginBottom: '20px',
  };

  const estiloContainer: React.CSSProperties = {
    marginLeft: '25%', 
    padding: '20px', 
  };

  const estiloGrid: React.CSSProperties = {
    display: 'flex',
    flexWrap: 'wrap',
    justifyContent: 'center',
    gap: '16px',
  };

  return (
    <>
      <Navbar />
      <div style={estiloContainer}>
        <h2 style={estiloCabecalho}>Pesquisas Inscritas</h2>
        <div style={estiloGrid}>
          {research.map((pesquisa) => (
           
            <PesquisaCard key={pesquisa.code} titulo={pesquisa.title} code={pesquisa.code} />
           
          ))}
        </div>
      </div>
    </>
  );
};

export default ListaPesquisas;
