package site.metacoding.animalprojectbackend.service.api;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;
import site.metacoding.animalprojectbackend.domain.sido.SidoDto;
import site.metacoding.animalprojectbackend.domain.sido.SidoRepository;
import site.metacoding.animalprojectbackend.domain.sigungu.ResponseDto;
import site.metacoding.animalprojectbackend.domain.sigungu.SigunguDto;
import site.metacoding.animalprojectbackend.domain.sigungu.SigunguRepository;

@RequiredArgsConstructor
@Service
public class TestService {

    private final SidoRepository sidoRepository;
    private final SigunguRepository sigunguRepository;

    @Transactional
    public List<SigunguDto> 테스트(SigunguDto sigunguDto) {

        List<ResponseDto> sigunguList = new ArrayList<>();
        List<SigunguDto> lists = new ArrayList<>();
        List<SidoDto> sidoRepo = sidoRepository.findCd(); // 시도 코드만 찾기
        // List<SidoDto> sidoEntity = sidoRepository.findAll(); // 시도 모두 찾기
        // Set<SidoDto> sidoHash = sidoRepository.findCdHash();
        ResponseDto response = new ResponseDto();
        RestTemplate restTemplate = new RestTemplate();

        // 서비스키
        String key = "jDqHGG%2BaNG47ijh6s3XzB%2BuF8fJOeovccnw%2FZtc9wLQUaKJumPo%2Frl1a2ygZ68dv9L0PD7drvpjPAeTnnB9f%2FQ%3D%3D";

        // 시도 코드에 따른 변수들
        // String gwangju = "6290000";
        // String busan = "6260000";

        // for (int i = 0; i < sidoEntity.size(); i++) { // 시도 사이즈만큼 반복
        try {

            for (int p = 0; p < sidoRepo.size(); p++) {
                StringBuffer urisb = new StringBuffer();
                urisb.append("http://apis.data.go.kr/1543061/abandonmentPublicSrvc/sigungu?");
                urisb.append("serviceKey=" + key);
                urisb.append("&upr_cd=");
                urisb.append(sidoRepo.get(p).getOrgCd());
                urisb.append("&_type=JSON");

                System.out.println(urisb.toString());

                URI uri = new URI(urisb.toString());

                response = restTemplate.getForObject(uri, ResponseDto.class);
                System.out.println(response);

                for (int k = 0; k < response.getResponse().getBody().getItems().getItem().size(); k++) {
                    sigunguList.add(response);
                }
                for (SigunguDto a : lists) {
                    SigunguDto result = new SigunguDto(
                            a.getId(),
                            a.getOrgCd(),
                            a.getOrgdownNm(),
                            a.getUprCd());
                    lists.add(result);
                    System.out.println(lists);
                    List<SigunguDto> sigunguEntity = sigunguRepository.saveAll(lists);
                    return sigunguEntity;
                }
            }
        } catch (Exception e) {
            for (int p = 0; p < sidoRepo.size(); p++) {
                StringBuffer urisb = new StringBuffer();
                urisb.append("http://apis.data.go.kr/1543061/abandonmentPublicSrvc/sigungu?");
                urisb.append("serviceKey=" + key);
                urisb.append("&upr_cd=");
                urisb.append(sidoRepo.get(p).getOrgCd());
                urisb.append("&_type=JSON");

                System.out.println(urisb.toString());

                try {
                    URI uri = new URI(urisb.toString());
                    response = restTemplate.getForObject(uri, ResponseDto.class);
                    for (int k = 0; k < response.getResponse().getBody().getItems().getItem().size(); k++) {
                        sigunguList.add(response);

                    }

                    System.out.println(sigunguList);

                    for (SigunguDto a : lists) {
                        SigunguDto result = new SigunguDto(
                                a.getId(),
                                a.getOrgCd(),
                                a.getOrgdownNm(),
                                a.getUprCd());
                        lists.add(result);
                        System.out.println(lists);
                        List<SigunguDto> sigunguEntity = sigunguRepository.saveAll(lists);
                        return sigunguEntity;
                    }
                } catch (Exception o) {
                    o.printStackTrace();
                }

            }
        }
        return null;

    }
}
// 한 대여섯번 반복하지만 일단 다 나오긴 함(근데 다운이 안되네...;;)