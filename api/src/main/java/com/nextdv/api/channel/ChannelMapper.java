package com.nextdv.api.channel;

import com.nextdv.domain.channel.Channel;
import java.util.List;

/**
 * 클래스명: ChannelMapper
 * 작성자: JBumLee
 *
 * Channel 도메인 객체를 ChannelResponse DTO로 변환하는 매퍼
 */
public class ChannelMapper {

  /**
   * 메소드이름: toResponse
   * Channel 도메인 객체를 응답 DTO로 변환한다
   *
   * @param channel 변환할 채널 도메인 객체
   * @return ChannelResponse DTO
   */
  public static ChannelResponse toResponse(Channel channel) {
    return new ChannelResponse(
        channel.getId(),
        channel.getUserId(),
        channel.getType(),
        channel.getName(),
        channel.getConfig(),
        channel.getCreatedAt()
    );
  }

  /**
   * 메소드이름: toResponseList
   * Channel 도메인 객체 목록을 응답 DTO 목록으로 변환한다
   *
   * @param channels 변환할 채널 도메인 객체 목록
   * @return ChannelResponse DTO 목록
   */
  public static List<ChannelResponse> toResponseList(List<Channel> channels) {
    return channels.stream().map(ChannelMapper::toResponse).toList();
  }
}
